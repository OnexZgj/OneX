package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.linsa.retrofitdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

//测试AppBarLayout的使用 By张更杰
public class AppBarLayoutActivity extends AppCompatActivity {

    @InjectView(R.id.tb_aabl_toolbar)
    Toolbar tbAablToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_app_bar_layout);
        ButterKnife.inject(this);

        initToolbar();

    }

    /**
     * 初始化ToolBar设置
     */
    private void initToolbar() {
        tbAablToolbar.setTitle("小天才");
        tbAablToolbar.setSubtitle("蓝精灵");
//        tbAablToolbar.setLogo(R.drawable.ic_33);
//        tbAablToolbar.setNavigationIcon(R.drawable.small);
    }
}
