package com.example.qiao.weixin2.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qiao.weixin2.R;
import com.example.qiao.weixin2.wave.WaveHelper;
import com.example.qiao.weixin2.wave.WaveView;

public class wave extends AppCompatActivity {
    private WaveHelper mWaveHelper;

    private int mBorderColor = Color.parseColor("#44FFFFFF");
    private int mBorderWidth = 20;
    private WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);

        waveView = (WaveView) findViewById(R.id.wave2);
        waveView.setBorder(mBorderWidth, mBorderColor);
        mWaveHelper = new WaveHelper(waveView);
        waveView.setShapeType(WaveView.ShapeType.CIRCLE);
        waveView.setWaveColor(
                Color.parseColor("#28f16d7a"),
                Color.parseColor("#3cf16d7a"));
        waveView.setWaveHeight(0.5f);
        mBorderColor = Color.parseColor("#6495ED");


    }

    @Override
    protected void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper.start();
    }
}
