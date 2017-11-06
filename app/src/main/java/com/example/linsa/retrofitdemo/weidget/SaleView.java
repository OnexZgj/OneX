package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.linsa.retrofitdemo.R;

/**
 * Created by Linsa on 2017/9/25:13:57.
 * des:
 */

public class SaleView extends android.support.v7.widget.AppCompatTextView {

    private Paint sidePaint;
    private int sideWidth=10;
    private int width;
    private int height;
    private float radius;

    private RectF bgRectF;

    private float baseLineY;

    private PorterDuffXfermode mPorterDuffXfermode;

    //背景的bitmap
    private Bitmap bgBitmap;

    private Paint srcPaint;
    private Bitmap bgSrc;
    //进度条
    private Bitmap fgSrc;

    //绘制文本
    private Paint textPaint;

    private Bitmap fgBitmap;
    private int totalCount;
    private int currentCount;
    /** 售出比例 */
    private double scale=0.0f;


    public SaleView(Context context) {
        this(context,null);
    }

    public SaleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        System.out.println(scale+"----------------------");


        //绘制边框
        drawSide(canvas);

        //绘制背景
        drawBg(canvas);

        //绘制进度
        drawFg(canvas);



        //进行绘制文本
        drawText(canvas);






//
//        if (totalCount == 0) {
//            scale = 0.0f;
//        } else {
//            scale = Float.parseFloat(new DecimalFormat("0.00").format((float) currentCount / (float) totalCount));
//        }

    }


    public void setTotalAndCurrentCount(int totalCount, int currentCount) {
        this.totalCount = totalCount;
        if (currentCount > totalCount) {
            currentCount = totalCount;
        }
        this.currentCount = currentCount;
        postInvalidate();
    }




    //绘制文本
    private void drawText(Canvas canvas) {



        Bitmap textBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas txtCanvas = new Canvas(textBitmap);

        if (scale<0.8){
            txtCanvas.drawText(getText().toString(),(width-textPaint.measureText(getText().toString()))/2, baseLineY, textPaint);
        }
        if (scale>0.8){
            txtCanvas.drawText("即将起飞",(width-textPaint.measureText(getText().toString()))/2, baseLineY, textPaint);
        }






        textPaint.setXfermode(mPorterDuffXfermode);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.WHITE);


        txtCanvas.drawRoundRect(
                new RectF(sideWidth, sideWidth, (width-textPaint.measureText(getText().toString()))/2, height - sideWidth),
                radius, radius, textPaint);
        canvas.drawBitmap(textBitmap, 0, 0, null);
        textPaint.setXfermode(null);

    }


    private void drawFg(Canvas canvas) {

        if (scale==0){
            return;
        }

        //这个bitmap,相当于我在下放代码如何画，都不能超过这个bitmap的大小
        Bitmap fgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas fgCanvas = new Canvas(fgBitmap);

//        fgCanvas.drawRoundRect(bgRectF, radius, radius, srcPaint);

        if (fgSrc == null) {
            fgSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.fg);
        }

        //------------------这里为什么是new RectF(),而不是使用bgRect???????
        //解答，以为这里是需要动态的控制，进度条的大小，而不是固定死的，所以需要在这里重新设置一个新的RectF
//
//        String scralStr = getTag().toString();
//        Double scale = Double.valueOf(scralStr);



        fgCanvas.drawRoundRect(
                new RectF(sideWidth, sideWidth, (float) ((width - sideWidth)*scale), height - sideWidth),
                radius, radius, srcPaint);
//       fgCanvas.drawRoundRect(bgRectF, radius, radius, srcPaint);

        srcPaint.setXfermode(mPorterDuffXfermode);

        fgCanvas.drawBitmap(fgSrc, null, bgRectF, srcPaint);

        canvas.drawBitmap(fgBitmap,0,0,null);

        srcPaint.setXfermode(null);

    }




    //绘制背景
    private void drawBg(Canvas canvas) {
        if (bgBitmap == null) {
            bgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas bgCanvas = new Canvas(bgBitmap);

        bgCanvas.drawRoundRect(bgRectF, radius, radius, srcPaint);

        if (bgSrc == null) {
            bgSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
        }

        srcPaint.setXfermode(mPorterDuffXfermode);

        bgCanvas.drawBitmap(bgSrc, null, bgRectF, srcPaint);

        canvas.drawBitmap(bgBitmap,0,0,null);

        srcPaint.setXfermode(null);

    }

    private void drawSide(Canvas canvas) {
        canvas.drawRoundRect(bgRectF,radius,radius,sidePaint);
    }

    //初始化画笔工具
    private void initPaint() {
        sidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sidePaint.setStyle(Paint.Style.STROKE);
        sidePaint.setStrokeWidth(sideWidth);
        sidePaint.setColor(Color.RED);

        srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取View的宽高
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        //圆角半径
        radius = height / 2.0f;
        //留出一定的间隙，避免边框被切掉一部分
        if (bgRectF == null) {
            bgRectF = new RectF(sideWidth, sideWidth, width - sideWidth, height - sideWidth);
        }

        if (baseLineY == 0.0f) {
            Paint.FontMetricsInt fm = textPaint.getFontMetricsInt();
            baseLineY = height / 2 - (fm.descent / 2 + fm.ascent / 2);
        }
    }


    public void setScale(float scale){
        this.scale=scale;
        postInvalidate();
    }




}
