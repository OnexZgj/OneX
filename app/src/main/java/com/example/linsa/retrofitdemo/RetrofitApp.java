package com.example.linsa.retrofitdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Linsa on 2017/7/21:10:33.
 * des:
 */

public class RetrofitApp extends Application {

    public static RetrofitApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        mInstance=this;
        init();
    }

    private void init() {
//
//        CrashHandler crashHandler = CrashHandler.newInstance();
//        crashHandler.init(getApplicationContext());
//
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
    }

    public static RetrofitApp getInstance(){
        return mInstance;
    }
}
