package com.example.linsa.retrofitdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Linsa on 2017/7/21:10:33.
 * des:
 */

public class RetrofitApp extends Application {

    public static RetrofitApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        init();
    }

    private void init() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public static RetrofitApp getInstance(){
        return mInstance;
    }
}
