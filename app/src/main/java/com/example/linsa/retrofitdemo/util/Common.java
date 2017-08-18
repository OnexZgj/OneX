package com.example.linsa.retrofitdemo.util;

import android.os.Environment;

/**
 * Created by Linsa on 2017/8/3:14:45.
 * des:
 */

public class Common {

    /**
     * 外部储存卡的绝对路径
     */
    public final static String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();


    /**
     * 电影导演item
     */
    public static final int MOVIE_DIRECT = 0;


    /**
     * 电影条目item
     */
    public static final int MOVIE = 2;
}
