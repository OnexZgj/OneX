package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Linsa on 2017/12/6:15:56.
 * des:
 */

public class ZoomView extends View {

    private Interpolator mInterpolator = new LinearInterpolator();
    private Paint mPaint;
    private boolean mIsRunning = false;
    private float mRadius=2000;
    private float mDx = getWidth() / 2;
    private float mDy = getHeight() / 2;
    private boolean mClose=false;

    public ZoomView(Context context) {
        this(context, null);
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        mPaint.setColor(Color.argb(120, 0, 255, 255));
    }

    public void setDxAndDy(boolean isClose,float dx, float dy){
        this.mClose=isClose;
        this.mDx=dx;
        this.mDy=dy;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(mDx,mDy , mRadius, mPaint);
    }

    /**
     * 开始
     */
    public void start() {
//        if (!mIsRunning) {
            mIsRunning = true;

            mCreateCircle.run();
//        }
    }

    private Runnable mCreateCircle = new Runnable() {
        @Override
        public void run() {
            if (mIsRunning) {
                if (mClose){
                    if (mRadius>0){
                        mRadius-=10;
                        postInvalidate();
                        postDelayed(mCreateCircle, 10);
                    }else{
                        mRadius=0;
                    }
                }else{

                    if (mRadius<1200){

                        mRadius+=10;
                        postInvalidate();
                        postDelayed(mCreateCircle, 10);
                    }else{
                        mRadius=1200;
                    }

                }


            }
        }
    };


    private class Circle {

        float getCurrentRadius() {

            return mInterpolator.getInterpolation(1200);
        }
    }


    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        if (mInterpolator == null) {
            mInterpolator = new LinearInterpolator();
        }
    }
}
