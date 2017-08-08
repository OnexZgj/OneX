package com.example.linsa.retrofitdemo.activity;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.linsa.retrofitdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TestVector extends AppCompatActivity {

    @InjectView(R.id.img_atv_show)
    ImageView imgAtvShow;
    @InjectView(R.id.btn_atv_show_img)
    Button btnAtvShowImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_vector);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_atv_show_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_atv_show_img:

                AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) imgAtvShow.getDrawable();

                drawable.start();
                break;
        }
    }
}
