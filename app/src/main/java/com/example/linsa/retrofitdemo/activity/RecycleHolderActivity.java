package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.adapter.NormalAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecycleHolderActivity extends AppCompatActivity {

    private ArrayList<String> mData;

    @InjectView(R.id.rv_arh_content)
    RecyclerView rvArhContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_holder);
        ButterKnife.inject(this);


        initData();


    }

    private void initData() {
        mData=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mData.add("I   am   the  : " +i);
        }

        rvArhContent.setLayoutManager(new LinearLayoutManager(this));
        rvArhContent.setAdapter(new NormalAdapter(mData));
    }
}
