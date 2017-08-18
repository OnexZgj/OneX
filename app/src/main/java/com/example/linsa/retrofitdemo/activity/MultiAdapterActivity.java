package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.adapter.MultiRecycleViewAdapter;
import com.example.linsa.retrofitdemo.bean.MovieDirector;
import com.example.linsa.retrofitdemo.service.DataService;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MultiAdapterActivity extends AppCompatActivity {

    @InjectView(R.id.rv_ama_show)
    RecyclerView rvAmaShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_adapter);
        ButterKnife.inject(this);

        List<MovieDirector> dataDirector = DataService.getDataDirector();

        MultiRecycleViewAdapter multiRecycleViewAdapter = new MultiRecycleViewAdapter(dataDirector);

        rvAmaShow.setLayoutManager(new LinearLayoutManager(this));
        rvAmaShow.setAdapter(multiRecycleViewAdapter);


    }
}
