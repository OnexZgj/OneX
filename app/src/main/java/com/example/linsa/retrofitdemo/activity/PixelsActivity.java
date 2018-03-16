package com.example.linsa.retrofitdemo.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.linsa.retrofitdemo.MyApp;
import com.example.linsa.retrofitdemo.broadcast.BootCompleteReceiver;

/**
 * 1px像素值保活进程
 */
public class PixelsActivity extends AppCompatActivity implements IPixelsActivity {

    private static PixelsActivity instance;
    private BootCompleteReceiver bootCompleteReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pixels);
        instance = this;
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
        initRegisterBroadCast();

    }

    private void initRegisterBroadCast() {
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        bootCompleteReceiver = new BootCompleteReceiver();
        registerReceiver(bootCompleteReceiver, filter);
    }


    public static void startPixels() {
        Intent intent = new Intent(MyApp.getInstance(), PixelsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApp.getInstance().startActivity(intent);
    }


    public static void killPixels() {
        if (instance != null) {
            instance.finish();
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(bootCompleteReceiver);
        instance=null;
        super.onDestroy();

    }
}
