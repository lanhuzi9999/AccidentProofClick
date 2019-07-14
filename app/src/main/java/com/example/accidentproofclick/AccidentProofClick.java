package com.example.accidentproofclick;

import android.util.Log;
import android.view.View;
import android.view.MotionEvent;

import com.example.accidentproofclick.reflect.ReflectHelper;

/**
 * @author: robin
 * @description:防止快速点击
 * @date: 2014/4/12
 **/
public class AccidentProofClick implements View.OnTouchListener {
    String TAG = "AccidentProofClick";
    /**
     * 最大点击时长
     */
    final int MAX_CLICK_DURATION = 350;
    /**
     * 两次点击时间间隔
     */
    final int MIN_CLICK_INTERVAL_TIME = 1000;
    /**
     * 记录上次击发点击事件时间
     */
    long mLastClickTime;
    float mLastX, mLastY;
    /**
     * 标记是否被按下
     */
    boolean mIsPressed;
    /**
     * 标记按下的View
     */
    View mPressedView;
    long mPressedTime;

    public AccidentProofClick() {
        TAG = getClass().getSimpleName();
        mLastX = 0.0f;
        mLastY = 0.0f;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        float deltaX = 0.0f, deltaY = 0.0f;
        long clickinterval = 0;
        long currenttime = System.currentTimeMillis();
        boolean handle = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                v.setPressed(true);
                mPressedTime = currenttime;
                mLastX = event.getX();
                mLastY = event.getY();
                mIsPressed = true;
                mPressedView = v;
                Log.i(TAG, "onTouch down x=" + mLastX + ",y=" + mLastY + ",view=" + v);
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = event.getX() - mLastX;
                deltaY = event.getY() - mLastY;
                Log.i(TAG, "onTouch move deltax=" + deltaX + ",deltaY=" + deltaY + ",view=" + v);
                break;
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                deltaX = event.getX() - mLastX;
                deltaY = event.getY() - mLastY;
                clickinterval = currenttime - mLastClickTime;
                if (clickinterval < 0) {
                    mLastClickTime = currenttime;
                }
                Log.i(TAG, "onTouch up deltax=" + deltaX + ",deltaY=" + deltaY + ",clickinterval=" + clickinterval
                        + ",ispressed=" + mIsPressed + ",view=" + v + ",sameview=" + (mPressedView == v));
                if (hasOnClickListeners(v)) {
                    long pressedtime = currenttime - mPressedTime;
                    final int X = (int) event.getX(), Y = (int) event.getY();
                    float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    final boolean pointerInView = X >= 0 && Y >= 0 && X <= v.getMeasuredWidth() && Y <= v.getMeasuredHeight();
                    if (pointerInView && mPressedView == v
                            && clickinterval > MIN_CLICK_INTERVAL_TIME
                            && distance < 30) {
                        mLastClickTime = currenttime;
                        v.performClick();
                    } else {
                        Log.i(TAG, "onTouch ignore click event pressedtime=" + pressedtime);
                    }
                    event.setAction(MotionEvent.ACTION_CANCEL);
                }
                mIsPressed = false;
                mPressedView = null;
                break;
            default:
                v.setPressed(false);
                mIsPressed = false;
                mPressedView = null;
                Log.i(TAG, "onTouch other action=" + action + ",view=" + v);
                break;
        }
        return handle;
    }

    boolean hasOnClickListeners(View v) {
        Object obj = ReflectHelper.callMethod(v, "hasOnClickListeners", null, null);
        if (obj != null && obj instanceof Boolean) {
            return (Boolean) obj;
        } else {
            obj = ReflectHelper.getDeclaredFieldValue(v, "android.view.View", "mOnClickListener");
            return obj != null;
        }
    }
}
