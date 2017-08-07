package com.example.linsa.retrofitdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.linsa.retrofitdemo.activity.PixelsActivity;

/**
 * Created by Linsa on 2017/8/7:11:10.
 * des:
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            PixelsActivity.startPixels();
        } else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            PixelsActivity.killPixels();
        }
    }
}
