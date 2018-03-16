package com.example.linsa.retrofitdemo.net;

import com.example.linsa.retrofitdemo.MyApp;
import com.example.linsa.retrofitdemo.util.CommonUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Linsa on 2017/7/21:10:58.
 * des:retrofit请求框架的完整封装
 */

public class RetrofitHelper {
    /**
     * 创建的okhttp的实例
     */
    private static OkHttpClient mOkHttpClient;



    /**
     * 静态代码块
     */
    static {
        initOkHttpClient();
    }


    /**
     * 在后期的代码中只需要进行添加这个相关的一些方法，也和retrofit的使用相关，不同的请求地址和参数，对应不同的接口和请求的方法。
     * @return
     */
    public static MovieService getMovieAPI() {
        return createApi(MovieService.class, HttpMethods.BASE_URL);
    }


    public static DesService getDesAPI(){
        return createApi(DesService.class,HttpMethods.BASE_URL2);
    }


    public static UpLoadFileService getUpLoadFileAPI(){
        return createApi(UpLoadFileService.class,HttpMethods.BASE_URL2);
    }





    /**
     * 进行初始化retrofit的代码和进行创建相应的网络请求的方法
     * @param clazz 定义了请求方法的接口
     * @param baseUrl 基于这个接口中的方法中的基地址
     * @return 接口的实例对象，可以进行直接调用请求方法
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
        return retrofit.create(clazz);
    }



    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,
     * 设置StethoInterceptor(),两个好处：1、方便调试在Chrome中 2、预防多次相同请求
     * 设置UA拦截器(带后续理解)
     */
    private static void initOkHttpClient() {
        if (mOkHttpClient==null){
            synchronized (RetrofitHelper.class){
                //设置了缓存的位置 大小为10M
                if (mOkHttpClient==null){
                    Cache cache = new Cache(new File(MyApp.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
                    mOkHttpClient=new OkHttpClient.Builder()
                            .addNetworkInterceptor(new StethoInterceptor())
//                            .addNetworkInterceptor(new CacheInterceptor())
                            .cache(cache)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15,TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20,TimeUnit.SECONDS).build();
                }
            }
        }

    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(MyApp.getInstance())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(MyApp.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }


}
