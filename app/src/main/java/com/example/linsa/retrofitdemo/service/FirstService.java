package com.example.linsa.retrofitdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Linsa on 2017/8/23:09:55.
 * des: 轮循Service进行保活
 */

public class FirstService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }


}
