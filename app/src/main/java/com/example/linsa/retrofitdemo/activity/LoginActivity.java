package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.bean.Movie;
import com.example.linsa.retrofitdemo.net.RetrofitHelper;
import com.example.linsa.retrofitdemo.designview.CircleProgressView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.delete_username)
    ImageButton deleteUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.pb_login)
    CircleProgressView pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
        pbLogin.setVisibility(View.VISIBLE);
//        pbLogin.spin();  和普通没有效果
//        pbLogin.stopSpinning(); 停止旋转

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        Toast.makeText(this, "点检；", Toast.LENGTH_SHORT).show();
        
        RetrofitHelper.getMovieAPI()
                .getTopMovie(0, 10)
                //指定被观察者所执行的线程，因为是联网请求数据，●Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
                .subscribeOn(Schedulers.io())
                //指定观察者所执行的线程，因为我们在这里请求到网络数据，并进行处理，所以需要进行放在Android的主线程中进行执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(LoginActivity.this, "request Success!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "request Failed !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Toast.makeText(LoginActivity.this, movie.getSubjects().get(5).getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
