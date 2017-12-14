package com.example.linsa.retrofitdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.util.Common;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * create by OneX on 2017/12/6
 * 测试CanvasApi
 */
public class AnalysisCanvasActivity extends AppCompatActivity {

    @InjectView(R.id.btn_aac_xy)
    Button btnAacXy;
    @InjectView(R.id.btn_aac_argb)
    Button btnAacArgb;
    @InjectView(R.id.btn_acc_text)
    Button btnAacText;
    @InjectView(R.id.btn_acc_path)
    Button btnAacPath;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_canvas);
        ButterKnife.inject(this);
        intent = new Intent(AnalysisCanvasActivity.this, CanvasContentActivity.class);
    }

    @OnClick({R.id.btn_acc_path, R.id.btn_acc_text, R.id.btn_aac_xy, R.id.btn_aac_argb})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_aac_xy:
                intent.putExtra("mType", Common.TYPE_XY);
                break;
            case R.id.btn_aac_argb:
                intent.putExtra("mType", Common.TYPE_ARGB);
                break;
            case R.id.btn_acc_text:
                intent.putExtra("mType", Common.TYPE_TEXT);
                break;
            case R.id.btn_acc_path:
                intent.putExtra("mType", Common.TYPE_PATH);
                break;
        }
        startActivity(intent);
    }
}
