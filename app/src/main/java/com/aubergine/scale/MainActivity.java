package com.aubergine.scale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Scale scale = (Scale)findViewById(R.id.scale);
        final TextView tvResult = (TextView)findViewById(R.id.tv_result);
        scale.setStartCm(21);
        scale.setPxPerMM(20);
        scale.setScaleUpdate(new OnScaleUpdate() {
            @Override
            public void onScaleSelected(float result) {
                tvResult.setText(result + " cm");
            }
        });


    }
}
