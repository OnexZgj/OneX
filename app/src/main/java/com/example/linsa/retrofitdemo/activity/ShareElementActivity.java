package com.example.linsa.retrofitdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.util.StateBarTranslucentUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShareElementActivity extends AppCompatActivity {

    @InjectView(R.id.tb_ase_top)
    Toolbar tbAseTop;
    @InjectView(R.id.iv_ase_taylor)
    ImageView ivAseTaylor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share_element);
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        ButterKnife.inject(this);




        initActionBar();
    }

    //初始化ActionBar
    private void initActionBar() {

        tbAseTop.setTitle("Test SceneTransitionAnimation");
        tbAseTop.setTitleTextColor(Color.RED);
        setSupportActionBar(tbAseTop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
