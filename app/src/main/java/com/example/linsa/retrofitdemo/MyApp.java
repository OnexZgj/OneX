package com.example.linsa.retrofitdemo;

import android.app.Application;

import com.example.linsa.retrofitdemo.util.MyCrashHandler;


/**
 * Created by Linsa on 2017/7/21:10:33.
 * des:
 */

public class MyApp extends Application {

    public static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance=this;
        init();
    }

    private void init() {

        MyCrashHandler crashHandler=MyCrashHandler.getInstance();
        crashHandler.init(this);
    }


    public static MyApp getInstance(){
        return mInstance;
    }
}
