package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.adapter.SwipeCardAdapter;
import com.example.linsa.retrofitdemo.bean.Movie;
import com.example.linsa.retrofitdemo.net.HttpMethods;
import com.example.linsa.retrofitdemo.net.RetrofitHelper;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SwipeCardActivity extends AppCompatActivity {

    @InjectView(R.id.sfav_adp_view)
    SwipeFlingAdapterView sfavAdpView;
    private List<Movie.SubjectsBean> mSubjects;

    private Subscription msubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_process);
        ButterKnife.inject(this);
        initData();
        initNetData();

    }


    private void initData() {

        Subscriber<Movie> subscriber = new Subscriber<Movie>() {

            private SwipeCardAdapter mCardAdapter;

            @Override
            public void onCompleted() {
                Toast.makeText(SwipeCardActivity.this, "请求数据成功!", Toast.LENGTH_SHORT).show();
                if (mSubjects != null && mSubjects.size() > 0) {
                    mCardAdapter = new SwipeCardAdapter(mSubjects);
                    sfavAdpView.setAdapter(mCardAdapter);
                }

                sfavAdpView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
                    @Override
                    public void removeFirstObjectInAdapter() {
                        mSubjects.remove(0);
                        mCardAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onLeftCardExit(Object o) {
                        Toast.makeText(SwipeCardActivity.this, "Left!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRightCardExit(Object o) {
                        Toast.makeText(SwipeCardActivity.this, "Right!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdapterAboutToEmpty(int i) {

                    }

                    @Override
                    public void onScroll(float v) {

                    }
                });

                sfavAdpView.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClicked(int i, Object o) {
                        Toast.makeText(SwipeCardActivity.this, "position : " + i, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SwipeCardActivity.this, "请求数据错误!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Movie movie) {
                mSubjects = new ArrayList<>();
                mSubjects = movie.getSubjects();
            }
        };

        HttpMethods.getInstance().getTopMovie(subscriber, 1, 10);

        //bilili请求网络写法


    }

    private void initNetData() {
        unsubscribe();
        Subscriber<Movie> subscriber=new Subscriber<Movie>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Movie movie) {

            }
        };

        if (msubscribe == null) {
            msubscribe = RetrofitHelper.getMovieAPI().getTopMovie(1, 10)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //观察者的运行的线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

    }

    //进行取消网络请求
    private void unsubscribe() {
        if (msubscribe != null && !msubscribe.isUnsubscribed()) {
            msubscribe.unsubscribe();
        }
    }
}
