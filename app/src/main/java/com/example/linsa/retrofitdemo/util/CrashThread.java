package com.example.linsa.retrofitdemo.util;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

/**
 * Created by nike 
 */
public class CrashThread extends Thread{
	private Context context;
    private String msg;
    
    public CrashThread(Context context, String msg){
    	this.context = context;
        this.msg = msg;
    }

    @Override
    public void run() {
        Looper.prepare();
        if (msg != null){
            Log.e("CrashHandler", "Error Msg : "+ msg);
        }
        Looper.loop();
    }
}
