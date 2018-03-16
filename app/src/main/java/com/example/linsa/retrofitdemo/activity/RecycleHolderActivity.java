package com.example.linsa.retrofitdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.adapter.HeaderAndFooterWrapper;
import com.example.linsa.retrofitdemo.adapter.NormalAdapter;
import com.example.linsa.retrofitdemo.weidget.LoadingView;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecycleHolderActivity extends AppCompatActivity {

    @InjectView(R.id.rv_arh_content)
    RecyclerView rvArhContent;
    @InjectView(R.id.swipeRefreshLayout)
    SHSwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<String> mData;
    private NormalAdapter strongAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_holder);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add("I   am   the  : " + i);
        }


        rvArhContent.setLayoutManager(new LinearLayoutManager(this));
        strongAdapter = new NormalAdapter(mData);

        HeaderAndFooterWrapper<RecyclerView.ViewHolder> wrapperAdapter = new HeaderAndFooterWrapper<>(strongAdapter);


        TextView tv = new TextView(this);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
        params.leftMargin=10;



        tv.setBackgroundColor(Color.BLUE);

        //---实现效果一样
        tv.requestLayout();
        tv.setLayoutParams(params);

        tv.setHeight(200);
        tv.setWidth(getResources().getDisplayMetrics().widthPixels);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setText(" I  AM HEAD  1");

        TextView tv2 = new TextView(this);
        tv2.setBackgroundColor(Color.GREEN);
        tv2.setHeight(500);
        tv2.setWidth(getResources().getDisplayMetrics().widthPixels);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextColor(Color.WHITE);
        tv2.setText(" I AM HEAD  2");

        LoadingView loadingView = new LoadingView(this);

        loadingView.addBitmap(R.mipmap.v4);
        loadingView.addBitmap(R.mipmap.v5);
        loadingView.addBitmap(R.mipmap.v6);
        loadingView.addBitmap(R.mipmap.v7);
        loadingView.addBitmap(R.mipmap.v8);
        loadingView.addBitmap(R.mipmap.v9);
        loadingView.setShadowColor(Color.LTGRAY);
        loadingView.setDuration(1000);

        loadingView.start();


        wrapperAdapter.addHeaderView(loadingView);
        wrapperAdapter.addHeaderView(tv);
        wrapperAdapter.addHeaderView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setBackgroundColor(Color.GREEN);
        tv3.setHeight(500);
        tv3.setWidth(getResources().getDisplayMetrics().widthPixels);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor(Color.WHITE);
        tv3.setText(" FOOTER  1");

        wrapperAdapter.addFootView(tv3);


        rvArhContent.setAdapter(wrapperAdapter);


        View headView = LayoutInflater.from(this).inflate(R.layout.layout_refresh_footer, null);
//        swipeRefreshLayout.setHeaderView(headView);

//        final LoadingView loadingView = (LoadingView) headView.findViewById(R.id.pb_loading);


        swipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.finishRefresh();
                        Toast.makeText(RecycleHolderActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1600);
            }

            @Override
            public void onLoading() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.finishLoadmore();
                        Toast.makeText(RecycleHolderActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1600);
            }

            /**
             * 监听下拉刷新过程中的状态改变
             * @param percent 当前下拉距离的百分比（0-1）
             * @param state 分三种状态{NOT_OVER_TRIGGER_POINT：还未到触发下拉刷新的距离；OVER_TRIGGER_POINT：已经到触发下拉刷新的距离；START：正在下拉刷新}
             */
            @Override
            public void onRefreshPulStateChange(float percent, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setRefreshViewText("下拉刷新");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setRefreshViewText("松开刷新");
                        break;
                    case SHSwipeRefreshLayout.START:
                        swipeRefreshLayout.setRefreshViewText("正在刷新");
                        break;
                }
            }

            @Override
            public void onLoadmorePullStateChange(float v, int state) {
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText("上拉加载");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        swipeRefreshLayout.setLoaderViewText("松开加载");
                        break;
                    case SHSwipeRefreshLayout.START:
                        swipeRefreshLayout.setLoaderViewText("正在加载");
                        break;
                }
            }


        });
    }


}
