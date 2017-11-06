package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Linsa on 2017/9/25:10:19.
 * des: 测试canvas的混合模式
 */

public class CircleRectView extends View {
    //图形的混合模式
    private PorterDuffXfermode porterDuffXfermode;

    public CircleRectView(Context context) {
        this(context, null);
    }

    public CircleRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //重写onDraw()方法，自定义绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//
        Paint paint = new Paint();
//        paint.setColor(0xFFFFCC44);
//        //直接绘制背景
//        canvas.drawARGB(255, 139, 197, 180);
//
//        int width = canvas.getWidth();
//        //绘制圆形
//
//        int radius = width / 3;
//
//
//        canvas.drawCircle(radius, radius, radius, paint);
//
//
//        //设置图层的混合模式
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//
//        paint.setColor(Color.CYAN);
//        /** 第一个参数是：矩形左边开始的坐标，对应的第三个参数是边的结束的坐标，相对的第二个参数和第四个参数相同 */
//        canvas.drawRect(radius, radius, radius * 3, radius * 3, paint);
//        paint.setXfermode(null);


//        //设置背景色
//        canvas.drawARGB(255, 139, 197, 186);
//
//        int canvasWidth = canvas.getWidth();
//        int r = canvasWidth / 3;
//        //正常绘制黄色的圆形
//        paint.setColor(0xFFFFCC44);
//        canvas.drawCircle(r, r, r, paint);
//        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        paint.setColor(0xFF66AAFF);
//        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
//        //最后将画笔去除Xfermode
//        paint.setXfermode(null);



//进行设置相关的模式
//        canvas.drawARGB(255, 139, 197, 186);

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        int r = canvasWidth / 3;
        //正常绘制黄色的圆形
        paint.setColor(0xFFFFCC44);
        canvas.drawCircle(r, r, r, paint);
        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形

        Paint mpaint=new Paint(Paint.ANTI_ALIAS_FLAG);

        mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        paint.setColor(0xFF66AAFF);
        mpaint.setTextSize(50);
        mpaint.setColor(Color.WHITE);

//        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
        //最后将画笔去除Xfermode
        canvas.drawText("我的天空",r,r,mpaint);

        mpaint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }


}
