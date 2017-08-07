package com.example.linsa.retrofitdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.activity.DragGridActivity;
import com.example.linsa.retrofitdemo.activity.LoginActivity;
import com.example.linsa.retrofitdemo.activity.PaletterActivity;
import com.example.linsa.retrofitdemo.activity.PixelsActivity;
import com.example.linsa.retrofitdemo.activity.TestExcelActivity;
import com.example.linsa.retrofitdemo.bean.Movie;
import com.example.linsa.retrofitdemo.broadcast.BootCompleteReceiver;
import com.example.linsa.retrofitdemo.net.HttpMethods;
import com.example.linsa.retrofitdemo.net.MovieService;
import com.example.linsa.retrofitdemo.presenter.IMainPresenter;
import com.example.linsa.retrofitdemo.util.ImageBlurUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.btn_am_drag)
    Button btnAmDrag;
    @InjectView(R.id.btn_am_paletter)
    Button btnAmPaletter;
    @InjectView(R.id.btn_cm_request)
    Button btnCmRequest;
    @InjectView(R.id.btn_cm_img)
    Button btnCmImg;
    @InjectView(R.id.btn_cm_login)
    Button btnCmLogin;
    @InjectView(R.id.btn_am_live_wallpaper)
    Button btnAmLiveWallpaper;
    @InjectView(R.id.btn_am_test_excel)
    Button btnAmTestExcel;
    @InjectView(R.id.iv_cm_aim)
    ImageView ivCmAim;
    @InjectView(R.id.btn_am_process)
    Button btnAmProcess;


    /**
     * 中间的协调层
     */
    private IMainPresenter iMainPresenter;

    /**
     * 进行测试Android中转化Excel
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


//        initRegisterBroadCast();

//        iMainPresenter = new MainPresenter(this);

    }


    /**
     * 动态注册锁屏的广播
     */
    private void initRegisterBroadCast() {
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BootCompleteReceiver(), filter);
    }


    @OnClick({R.id.btn_am_process,R.id.btn_am_drag, R.id.btn_am_paletter, R.id.btn_cm_request, R.id.btn_cm_img, R.id.btn_cm_login, R.id.btn_am_live_wallpaper, R.id.btn_am_test_excel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_am_drag:
                startActivity(new Intent(MainActivity.this, DragGridActivity.class));
                break;
            case R.id.btn_am_paletter:
                startActivity(new Intent(MainActivity.this, PaletterActivity.class));
                break;
            case R.id.btn_cm_request:
                getMovieThird();
                break;
            case R.id.btn_cm_img:
                imgblur();
                break;
            case R.id.btn_cm_login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.btn_am_live_wallpaper:
                startActivity(new Intent(MainActivity.this, LiveWallPaper.class));
                break;
            case R.id.btn_am_test_excel:
                startActivity(new Intent(MainActivity.this, TestExcelActivity.class));
                break;
            case R.id.btn_am_process:
                startActivity(new Intent(MainActivity.this,PixelsActivity.class));
                break;

        }
    }


    /**
     * 当按返回键重新进行开启Activity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        getApplicationContext().startActivity(intent);
    }

    /**
     * 将图片进行模糊的方法
     */
    private void imgblur() {
        Bitmap blur = ImageBlurUtil.blur(MainActivity.this, BitmapFactory.decodeResource(getResources(), R.drawable.icone), 20);
        ivCmAim.setImageBitmap(blur);
    }

    public void getMovie() {

        //------------简单的retrofit的使用------没有经过任何的封装---使用了Rxjava的框架
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //添加RxJava进行异步的处理
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        //这个其实是得到一个被观察者对象
        movieService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {
                        Log.i("TAG", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Log.i("TAG", "onNext: " + movie.getSubjects().get(2).getTitle());
                        Toast.makeText(MainActivity.this, movie.getSubjects().get(2).getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });


        //-----------------没有使用Rxjava的时候使用retrofit的调用-----================-------------------------------

//        MovieService movieService = retrofit.create(MovieService.class);
//        Call<Movie> call = movieService.getTopMovie(0, 10);
//
//        call.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                Movie body = response.body();
//                String s = body.getSubjects().get(0).getTitle();
//                Toast.makeText( MainActivity.this,s , Toast.LENGTH_SHORT).show();
//                Log.i("TAG", "onResponse: "+s);
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "request Faild!", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void getMovieThird() {

        Subscriber<Movie> subscriber = new Subscriber<Movie>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.i("TAG", "onError:   " + e.toString());
                Toast.makeText(MainActivity.this, "网络请求出错!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Movie movie) {
                Toast.makeText(MainActivity.this, movie.getSubjects().get(3).getTitle(), Toast.LENGTH_SHORT).show();
            }
        };
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 4);
    }


}
