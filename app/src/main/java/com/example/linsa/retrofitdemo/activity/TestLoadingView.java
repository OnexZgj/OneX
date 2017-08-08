package com.example.linsa.retrofitdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.designview.LoadingView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TestLoadingView extends AppCompatActivity {

    @InjectView(R.id.lv_atlv_loading)
    LoadingView lvAtlvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_loading_view);
        ButterKnife.inject(this);

        lvAtlvLoading.addBitmap(R.mipmap.v4);
        lvAtlvLoading.addBitmap(R.mipmap.v5);
        lvAtlvLoading.addBitmap(R.mipmap.v6);
        lvAtlvLoading.addBitmap(R.mipmap.v7);
        lvAtlvLoading.addBitmap(R.mipmap.v8);
        lvAtlvLoading.addBitmap(R.mipmap.v9);
        lvAtlvLoading.setShadowColor(Color.LTGRAY);
        lvAtlvLoading.setDuration(700);
        lvAtlvLoading.start();
    }
}
