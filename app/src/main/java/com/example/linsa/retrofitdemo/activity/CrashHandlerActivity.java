package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CrashHandlerActivity extends AppCompatActivity {

    @InjectView(R.id.btn_ach_ex)
    Button btnAchEx;
    @InjectView(R.id.tv_ach_show_info)
    TextView tvAchShowInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_handler);
        ButterKnife.inject(this);
        btnAchEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("this is my own Exception !");
            }
        });
    }

//    @OnClick({R.id.btn_ach_ex})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_ach_ex:
//                throw new RuntimeException("this is my own Exception !");
//        }
//    }
}
