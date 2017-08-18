package com.example.linsa.retrofitdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.adapter.BaseQuickAdapterItem;
import com.example.linsa.retrofitdemo.bean.Movie;
import com.example.linsa.retrofitdemo.net.HttpMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;

public class BaseAdapterHelperActivity extends AppCompatActivity {

    @InjectView(R.id.rv_abah_show)
    RecyclerView rvAbahShow;
    @InjectView(R.id.srl_abah_refresh)
    SwipeRefreshLayout srlAbahRefresh;
    private List<Movie.SubjectsBean> mDataList;
    private List<Movie.SubjectsBean> mRequestList;
    private BaseQuickAdapter mQuickAdapter;

    private int startIndex = 0;
    private int endIndex = 30;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter_helper);
        ButterKnife.inject(this);
        mDataList = new ArrayList<>();
        mRequestList = new ArrayList<>();

        initData(startIndex, endIndex);


    }


    private void initData(int startIndex, int endIndex) {


        mRequestList.clear();
        srlAbahRefresh.setRefreshing(true);
        srlAbahRefresh.setEnabled(true);


        HttpMethods.getInstance().getTopMovie(new Subscriber<Movie>() {

            @Override
            public void onCompleted() {
                initAdapter();
                Toast.makeText(BaseAdapterHelperActivity.this, mDataList.size() + "", Toast.LENGTH_SHORT).show();
                srlAbahRefresh.setRefreshing(false);

            }

            @Override
            public void onError(Throwable e) {
                srlAbahRefresh.setRefreshing(false);
                srlAbahRefresh.setEnabled(false);
                Toast.makeText(BaseAdapterHelperActivity.this, "请求数据失败,展示相关错误的界面", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Movie movie) {
                mRequestList = movie.getSubjects();
                mDataList.addAll(mRequestList);
            }
        }, startIndex, endIndex);

        Log.i("TAG", "initData: " + startIndex + " : " + endIndex);

    }

    private void initAdapter() {

        if (mQuickAdapter == null) {
            mQuickAdapter = new BaseQuickAdapterItem(R.layout.item_base_quick_adapter, mDataList);

            mQuickAdapter.addHeaderView(View.inflate(BaseAdapterHelperActivity.this, R.layout.activity_test_vector, null));

            rvAbahShow.setLayoutManager(new LinearLayoutManager(this));

            mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

//            mQuickAdapter.setLoadMoreView(new RecycleLoadView());

            //进行设置数据源的操作，与addData()相对应
//            mQuickAdapter.setNewData(mDataList);

            //动画每次加载都会执行
            mQuickAdapter.isFirstOnly(false);
            rvAbahShow.setAdapter(mQuickAdapter);

        }

        initListener();

    }


    //初始化RecycleView的监听器
    private void initListener() {

        if (mQuickAdapter != null) {


            mQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    Movie.SubjectsBean item = (Movie.SubjectsBean) mQuickAdapter.getItem(position);

                    Intent intent = new Intent(BaseAdapterHelperActivity.this, Html5Activity.class);
                    intent.putExtra("url", item.getAlt());
                    startActivity(intent);
                }
            });

            mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    Subscriber<Movie> subscriber = new Subscriber<Movie>() {
                        @Override
                        public void onCompleted() {
                            mQuickAdapter.addData(mRequestList);
                            mQuickAdapter.loadMoreComplete();
                            Toast.makeText(BaseAdapterHelperActivity.this, "加载更多完成!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(BaseAdapterHelperActivity.this, "加载更多的时，数据加载失败!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(Movie movie) {
                            mRequestList = movie.getSubjects();
                        }
                    };
                    HttpMethods.getInstance().getTopMovie(subscriber, endIndex, endIndex += 30);
                }
            });

        }

    }


    public JsonRequestFinishComplete mListener;


    public interface JsonRequestFinishComplete {
        void onRequestSuccess(List<Movie.SubjectsBean> list);

        void onRequestFail();
    }


    public void setOnJsonRequestFinishComplete(JsonRequestFinishComplete listenter) {
        this.mListener = listenter;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.baseadapteritem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.style:
                Toast.makeText(this, "切换主题!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
