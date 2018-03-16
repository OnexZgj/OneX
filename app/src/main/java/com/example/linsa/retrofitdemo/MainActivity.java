package com.example.linsa.retrofitdemo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.linsa.retrofitdemo.activity.AnalysisCanvasActivity;
import com.example.linsa.retrofitdemo.activity.AppBarLayoutActivity;
import com.example.linsa.retrofitdemo.activity.BaseAdapterHelperActivity;
import com.example.linsa.retrofitdemo.activity.CoordinatorLayoutActivity;
import com.example.linsa.retrofitdemo.activity.CrashHandlerActivity;
import com.example.linsa.retrofitdemo.activity.DragGridActivity;
import com.example.linsa.retrofitdemo.activity.DraggleAdapterActivity;
import com.example.linsa.retrofitdemo.activity.Html5Activity;
import com.example.linsa.retrofitdemo.activity.ListViewActivity;
import com.example.linsa.retrofitdemo.activity.LoginActivity;
import com.example.linsa.retrofitdemo.activity.MultiAdapterActivity;
import com.example.linsa.retrofitdemo.activity.MyActivity;
import com.example.linsa.retrofitdemo.activity.OpeGlActivity;
import com.example.linsa.retrofitdemo.activity.OptionsCompatActivity;
import com.example.linsa.retrofitdemo.activity.PaletterActivity;
import com.example.linsa.retrofitdemo.activity.PixelsActivity;
import com.example.linsa.retrofitdemo.activity.PorterDuffXfermodeActivity;
import com.example.linsa.retrofitdemo.activity.RecycleHolderActivity;
import com.example.linsa.retrofitdemo.activity.RecycleLoadmoreActivity;
import com.example.linsa.retrofitdemo.activity.SliderLayoutActivity;
import com.example.linsa.retrofitdemo.activity.TestExcelActivity;
import com.example.linsa.retrofitdemo.activity.TestHutils;
import com.example.linsa.retrofitdemo.activity.TestLoadingView;
import com.example.linsa.retrofitdemo.activity.TestPercentActivity;
import com.example.linsa.retrofitdemo.activity.TestThemeActivity;
import com.example.linsa.retrofitdemo.activity.TestUploadFileActivity;
import com.example.linsa.retrofitdemo.activity.TestVector;
import com.example.linsa.retrofitdemo.activity.UpLoadFileActivity;
import com.example.linsa.retrofitdemo.activity.ViewAnimationUtilsActivity;
import com.example.linsa.retrofitdemo.activity.ZhiHuActivity;
import com.example.linsa.retrofitdemo.bean.DesContent;
import com.example.linsa.retrofitdemo.bean.Movie;
import com.example.linsa.retrofitdemo.net.HttpMethods;
import com.example.linsa.retrofitdemo.net.MovieService;
import com.example.linsa.retrofitdemo.net.RetrofitHelper;
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
    @InjectView(R.id.btn_am_test_vector)
    Button btnAmTestVector;
    @InjectView(R.id.btn_am_test_loading)
    Button btnAmTestLoading;
    @InjectView(R.id.btn_am_test_hutils)
    Button btnAmTestHutils;
    @InjectView(R.id.btn_am_test_coor)
    Button btnAmTestCoor;
    @InjectView(R.id.btn_am_app_bar)
    Button btnAmAppBar;
    @InjectView(R.id.btn_am_ali_pay)
    Button btnAmAliPay;
    @InjectView(R.id.btn_am_test_slider_layout)
    Button btnAmTestSliderLayout;
    @InjectView(R.id.btn_am_reactive_network)
    Button btnAmReactiveNetwork;
    @InjectView(R.id.btn_am_slding_layout)
    Button btnAmSldingLayout;
    @InjectView(R.id.btn_am_percent_layout)
    Button btnAmPercentLayout;
    @InjectView(R.id.btn_am_multiple_adapter)
    Button btnAmMultipleAdapter;
    @InjectView(R.id.btn_am_test_webview)
    Button btnAmTestWebview;
    @InjectView(R.id.btn_am_test_adapter)
    Button btnAmTestAdapter;
    @InjectView(R.id.btn_am_drag_adapter)
    Button btnAmDragAdapter;
    @InjectView(R.id.btn_am_activity_options)
    Button btnAmActivityOptions;
    @InjectView(R.id.btn_am_double_process)
    Button btnAmDoubleProcess;
    @InjectView(R.id.btn_am_activity_uploadFile)
    Button btnAmActivityUploadFile;
    @InjectView(R.id.btn_am_opengl)
    Button btnAmOpengl;
    @InjectView(R.id.btn_am_recycleHoder)
    Button btnAmRecycleHoder;
    @InjectView(R.id.btn_am_pdm)
    Button btnAmPdm;
    @InjectView(R.id.btn_am_load_more_recycle)
    Button btnAmLoadMoreRecycle;
    @InjectView(R.id.btn_am_zh_recycle)
    Button btnAmZhRecycle;
    @InjectView(R.id.btn_am_zh_analysis_canvas)
    Button btnAmZhAnalysisCanvas;
    @InjectView(R.id.btn_am_view_animation_utils)
    Button btnAmViewAnimationUtils;
    @InjectView(R.id.btn_am_crash)
    Button btnAmCrash;

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
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        TextView tv = new TextView(this);
        tv.setText("F");
        tv.setBackgroundColor(Color.RED);

//        AlwaysShowToast toast = new AlwaysShowToast(getApplicationContext());
//        toast.setView(tv, 80, 36);
//        toast.show();
//        toast.setGravity(Gravity.CENTER, 0, 0);

        Glide.with(this).load("https://img3.doubanio.com/img/celebrity/large/17525.jpg")
                .error(R.drawable.taylor)
                .placeholder(R.drawable.lodingview)
                .centerCrop()
                .crossFade(5000)
                .into(ivCmAim);


//        ObjectAnimator alpha = ObjectAnimator.ofFloat(ivCmAim, "alpha", 1f, 0.8f);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivCmAim, "scaleX", 1f, 1.5f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivCmAim, "scaleY", 1f, 1.1f);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.setDuration(6000);
//        animatorSet.play(alpha).with(scaleX).with(scaleY);
//        animatorSet.start();


    }


    @OnClick({R.id.btn_am_retrofit_all, R.id.btn_am_crash, R.id.btn_am_view_animation_utils, R.id.btn_am_zh_analysis_canvas, R.id.btn_am_zh_recycle, R.id.btn_am_load_more_recycle, R.id.btn_am_pdm, R.id.btn_am_recycleHoder, R.id.btn_am_opengl, R.id.btn_am_activity_uploadFile, R.id.btn_am_double_process, R.id.btn_am_activity_options, R.id.btn_am_drag_adapter, R.id.btn_am_multiple_adapter, R.id.btn_am_test_adapter, R.id.btn_am_test_webview, R.id.btn_am_percent_layout, R.id.btn_am_reactive_network, R.id.btn_am_slding_layout, R.id.btn_am_test_slider_layout, R.id.btn_am_ali_pay, R.id.btn_am_app_bar, R.id.btn_am_test_coor, R.id.btn_am_test_hutils, R.id.btn_am_test_loading, R.id.btn_am_test_vector, R.id.btn_am_process, R.id.btn_am_drag, R.id.btn_am_paletter, R.id.btn_cm_request, R.id.btn_cm_img, R.id.btn_cm_login, R.id.btn_am_live_wallpaper, R.id.btn_am_test_excel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_am_drag:
                startActivity(new Intent(MainActivity.this, DragGridActivity.class));
                break;
            case R.id.btn_am_paletter:
                startActivity(new Intent(MainActivity.this, PaletterActivity.class));
                break;
            case R.id.btn_cm_request:
                getDesContentSecond();
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
                startActivity(new Intent(MainActivity.this, PixelsActivity.class));
                break;
            case R.id.btn_am_test_vector:
                startActivity(new Intent(MainActivity.this, TestVector.class));
                break;
            case R.id.btn_am_test_loading:
                startActivity(new Intent(MainActivity.this, TestLoadingView.class));
                break;
            case R.id.btn_am_test_hutils:
                startActivity(new Intent(MainActivity.this, TestHutils.class));
                break;
            case R.id.btn_am_test_coor:
                startActivity(new Intent(MainActivity.this, CoordinatorLayoutActivity.class));
                break;
            case R.id.btn_am_app_bar:
                startActivity(new Intent(MainActivity.this, AppBarLayoutActivity.class));
                break;
            case R.id.btn_am_ali_pay:
                startActivity(new Intent(MainActivity.this, ListViewActivity.class));
                break;
            case R.id.btn_am_test_slider_layout:
                startActivity(new Intent(MainActivity.this, SliderLayoutActivity.class));
                break;
            case R.id.btn_am_reactive_network:
                //进行异步监测网络状态的改变的一个功能
                break;
            case R.id.btn_am_slding_layout:
                startActivity(new Intent(MainActivity.this, TestThemeActivity.class));
                break;
            case R.id.btn_am_percent_layout:
                startActivity(new Intent(MainActivity.this, TestPercentActivity.class));
                break;
            case R.id.btn_am_test_webview:
                startActivity(new Intent(MainActivity.this, Html5Activity.class));
                break;
            case R.id.btn_am_test_adapter:
                startActivity(new Intent(MainActivity.this, BaseAdapterHelperActivity.class));
                break;
            case R.id.btn_am_multiple_adapter:
                startActivity(new Intent(MainActivity.this, MultiAdapterActivity.class));
                break;
            case R.id.btn_am_drag_adapter:
                startActivity(new Intent(MainActivity.this, DraggleAdapterActivity.class));
                break;
            case R.id.btn_am_activity_options:

//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        MainActivity.this,
//                        ivCmAim,
//                        "OneX_Zgj"
//                );


//                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeCustomAnimation(MainActivity.this,R.anim.translate_in,R.anim.translate_out);
//                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeScaleUpAnimation(ivCmAim,ivCmAim.getWidth(),ivCmAim.getHeight(),0,0);


//                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
//                ActivityCompat.startActivity(MainActivity.this,intent,optionsCompat.toBundle());


                startActivity(new Intent(MainActivity.this, OptionsCompatActivity.class));

                break;
            case R.id.btn_am_double_process:
                startActivity(new Intent(MainActivity.this, MyActivity.class));
                break;
            case R.id.btn_am_activity_uploadFile:
                startActivity(new Intent(MainActivity.this, UpLoadFileActivity.class));
                break;
            case R.id.btn_am_opengl:

                startActivity(new Intent(MainActivity.this, OpeGlActivity.class));
                break;
            case R.id.btn_am_recycleHoder:
                startActivity(new Intent(MainActivity.this, RecycleHolderActivity.class));
                break;
            case R.id.btn_am_pdm:
                startActivity(new Intent(MainActivity.this, PorterDuffXfermodeActivity.class));
                break;
            case R.id.btn_am_load_more_recycle:
                startActivity(new Intent(MainActivity.this, RecycleLoadmoreActivity.class));
                break;
            case R.id.btn_am_zh_recycle:
                startActivity(new Intent(MainActivity.this, ZhiHuActivity.class));
                break;
            case R.id.btn_am_zh_analysis_canvas:
                startActivity(new Intent(MainActivity.this, AnalysisCanvasActivity.class));
                break;
            case R.id.btn_am_view_animation_utils:
                startActivity(new Intent(MainActivity.this, ViewAnimationUtilsActivity.class));
                break;
            case R.id.btn_am_crash:
                startActivity(new Intent(MainActivity.this, CrashHandlerActivity.class));
                break;
            case R.id.btn_am_retrofit_all:
                startActivity(new Intent(MainActivity.this, TestUploadFileActivity.class));
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


    /**
     * 测试okhttpClient的一些使用
     */
    public void getDesContent() {


        Subscriber<DesContent> subscriber = new Subscriber<DesContent>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Des Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.i("TAG", "onError:   " + e.toString());
                Toast.makeText(MainActivity.this, "网络请求出错!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(DesContent desContent) {
                Toast.makeText(MainActivity.this, desContent.getTotal() + "...", Toast.LENGTH_SHORT).show();
            }
        };
        HttpMethods.getInstance().getDescontent(subscriber);


    }


    public void getDesContentSecond() {
        RetrofitHelper.getDesAPI().getDesContent().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DesContent>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "get Data complete!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "get Data Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(DesContent desContent) {
                        Toast.makeText(MainActivity.this, "---" + desContent.getStart() + "---", Toast.LENGTH_SHORT).show();
                    }
                });

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
