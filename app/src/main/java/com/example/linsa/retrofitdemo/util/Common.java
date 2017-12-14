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

    /**
     * 绘制XY类型
     */
    public static final int TYPE_XY = 100;

    /**
     * 绘制ARGB
     */
    public static final int TYPE_ARGB = 101;

    /**
     * 绘制文本
     */
    public static final int TYPE_TEXT = 102;
    /**
     * 绘制点
     */
    public static final int TYPE_POINT = 103;

    /**
     * 绘制线
     */
    public static final int TYPE_LINE = 104;

    /**
     * 绘制矩形
     */
    public static final int TYPE_RECT = 105;

    /**
     * 绘制圆
     */
    public static final int TYPE_CIRCLE = 106;

    /**
     * 绘制椭圆
     */
    public static final int TYPE_OVAL = 107;

    /**
     * 绘制弧度
     */
    public static final int TYPE_ARC = 108;

    /**
     * 绘制path
     */
    public static final int TYPE_PATH = 109;

    /**
     * 绘制bitmap
     */
    public static final int TYPE_BITMAP = 110;

}
