package com.example.linsa.retrofitdemo.net;

import com.example.linsa.retrofitdemo.RetrofitApp;
import com.example.linsa.retrofitdemo.bean.Movie;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * retrofit的入门封装
 * Created by Linsa on 2017/7/20.
 */

public class HttpMethods {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 5;
    private final MovieService movieService;

    private HttpMethods() {

//        Stetho 是一个 Facebook 出品的超赞的开源库，它可以让你用 Chrome 的功能—— 开发者工具 来检查调试Android 应用。
//        Stetho 不仅能够检查应用的 SQLite 数据库和视图层次，还可以检查 OkHttp 的每一条请求和响应消息：
//        这种自我检查方式（Introspection）有效地确保了服务器返回允许缓存资源的 HTTP 首部时，且核缓存资源存在时，不再发出任何请求。

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();

        Cache cache = new Cache(new File(RetrofitApp.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);

        //设置超时时间，并且设置时间的单位
        okBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                //设置okhttp的缓存的机制
                .addNetworkInterceptor(new StethoInterceptor())
                .cache(cache);

        //进行retrofit的初始化操作
        Retrofit retrofit = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(okBuilder.build())
                .build();

        //
        movieService = retrofit.create(MovieService.class);

    }


    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }


    public void getTopMovie(Subscriber<Movie> subscriber, int start, int count) {
        if (movieService != null) {
            movieService.getTopMovie(start, count)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


}
