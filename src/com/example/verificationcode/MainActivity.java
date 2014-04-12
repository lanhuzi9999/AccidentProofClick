package com.example.verificationcode;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView2);
		button = (Button) findViewById(R.id.button1);
		verificationCode();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				verificationCode();
			}
		});
	}

	private void verificationCode() {
		char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L',
				'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'3', '4', '5', '6', '7', '8', '9' };
		// 标记数组，用来标记哪些位置被选择过，哪些位置没有被选择过。布尔型数组的默认值是false
		boolean[] flag = new boolean[chars.length];

		char[] result = new char[5];
		for (int i = 0; i < result.length; i++) {
			int j;
			do {
				j = new Random().nextInt(chars.length);

			} while (flag[j]);
			result[i] = chars[j];
			flag[j] = true;
		}
		String string = "";
		for (int i = 0; i < result.length; i++) {
			string += result[i];
		}
		textView.setText(string);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
