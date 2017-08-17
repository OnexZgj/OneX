package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.linsa.retrofitdemo.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * https://github.com/HomHomLin/SlidingLayout
 */
public class SldingLayoutActivity extends AppCompatActivity {

    @InjectView(R.id.rv_asl_content)
    RecyclerView rvAslContent;
    private ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slding_layout);
        ButterKnife.inject(this);

        initData();

        //如果子View是滑动的View的时候，只有设置的滑动为top的时候，控件中的item才可以完全的显示出来


    }

    private void initData() {
        datas=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datas.add("OneX  :  "+i);
        }
        rvAslContent.setLayoutManager(new LinearLayoutManager(this));
        rvAslContent.setAdapter(new MyAdapter(datas));
    }


}
