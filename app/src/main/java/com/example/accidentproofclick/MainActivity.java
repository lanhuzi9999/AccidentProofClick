package com.example.accidentproofclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView accidentProofClickTv = findViewById(R.id.accident_proofclick);
        //使用
        AccidentProofClick mAccidentProofClick = new AccidentProofClick();
        accidentProofClickTv.setOnTouchListener(mAccidentProofClick);
    }
}
