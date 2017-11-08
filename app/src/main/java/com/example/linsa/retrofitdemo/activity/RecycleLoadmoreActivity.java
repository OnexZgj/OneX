package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.adapter.LoadMoreAdapter;
import com.example.linsa.retrofitdemo.listener.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * RecycleView自己实现加载更多的Activity
 */
public class RecycleLoadmoreActivity extends AppCompatActivity {

    @InjectView(R.id.arl_rv_recycleview)
    RecyclerView arlRvRecycleview;
    @InjectView(R.id.arl_srl_refresh)
    SwipeRefreshLayout arlSrlRefresh;

    private LoadMoreAdapter loadMoreAdapter;

    private ArrayList<String> dataList = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_loadmore);
        ButterKnife.inject(this);

        initListener();

        loadData();

        initAdapter();

    }

    /**
     * 加载监听
     */
    private void initListener() {
        arlSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataList.clear();
                loadData();
                loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING_COMPLETE);
                loadMoreAdapter.notifyDataSetChanged();


                // 延时1s关闭下拉刷新
                arlSrlRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (arlSrlRefresh != null && arlSrlRefresh.isRefreshing()) {
                            arlSrlRefresh.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

    }

    private void initAdapter() {
        loadMoreAdapter = new LoadMoreAdapter(dataList);
        arlRvRecycleview.setAdapter(loadMoreAdapter);

        gridLayoutManager = new GridLayoutManager(this, 2);
        arlRvRecycleview.setLayoutManager(gridLayoutManager);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                final int viewType = arlRvRecycleview.getAdapter().getItemViewType(position);
                if (viewType == loadMoreAdapter.TYPE_ITEM) {
                    return 1;
                } else if (viewType==loadMoreAdapter.TYPE_FOOTER) {
                    return gridLayoutManager.getSpanCount();
                }else {
                    return 0;
                }
            }
        });




        arlRvRecycleview.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMoreData() {
                loadMoreAdapter.setLoadState(loadMoreAdapter.LOADING);

                if (dataList.size() < 52) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadData();
                                    loadMoreAdapter.setLoadState(
                                            loadMoreAdapter.LOADING_COMPLETE);
                                }
                            });
                        }
                    },3000);
                }else{
                    loadMoreAdapter.setLoadState(
                            loadMoreAdapter.LOADING_END);
                }
            }
        });
    }

    /**
     * 加载数据
     */
    private void loadData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            dataList.add(String.valueOf(letter));
            letter++;
        }
    }
}
