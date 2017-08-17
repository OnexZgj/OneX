package com.example.linsa.retrofitdemo.weidget;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Linsa on 2017/8/11:15:25.
 * des:
 */

public class WaveView extends View {


//    y = A×sin(x × Φ + offset) + H，A代表了波浪的振幅，x代表了当前view的宽度的位置，Φ代表了2 Math.PI / width,H代表了Y轴上的基本高度。


    //y轴上面的基本高度
    private final int INIT_BASE_HEIGHT1 = 100;
    private final int INIT_BASE_HEIGHT2 = 100;
    private final int INIT_BASE_HEIGHT3 = 100;

    private int mHeight;
    private int mWidth;
    private float[] mContentOneYs = null;
    private float[] mContentTwoys = null;
    private float[] mContentThreeys = null;


    private float[] mRestoreOnes = null;
    private float[] mRestoreTwos = null;
    private float[] mRestoreThrees = null;


    //第一条直线的振幅
    private static final int SWINGONE = 40;
    //第二条直线的振幅
    private static final int SWINGTWO = 80;


    private static final int SWINGTHREE = 60;


    private static final int OFFSETONE = 0;

    private static final int OFFSETTWO = 40;

    private static final int OFFSETTHREE = 80;


    private int mPosition1 = 0;
    private int mPosition2 = 0;
    private int mPosition3 = 0;
    private static final int STEP1 = 5;
    private static final int STEP2 = 8;
    private static final int STEP3 = 100;
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setColor(Color.parseColor("#AB9DCF"));
        mPaint1.setStrokeWidth(4);
        mPaint1.setAlpha(155);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(Color.parseColor("#A2D1F3"));
        mPaint2.setStrokeWidth(4);
        mPaint2.setAlpha(155);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setColor(Color.parseColor("#00FFFF"));
        mPaint3.setStrokeWidth(4);
        mPaint3.setAlpha(155);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != 0 || h != 0 || w != oldw || h != oldh) {
            mWidth = w;
            mHeight = h;
            calculatePoints();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        changeRestorePosition();
        for (int i = 0; i < mWidth; i++) {
            final int x = i;
            final float y1 = mRestoreOnes[i];
            final float y2 = mRestoreTwos[i];
            final float y3 = mRestoreThrees[i];
            canvas.drawLine(x, y2, x, mHeight, mPaint2);
            canvas.drawLine(x, y1, x, mHeight, mPaint1);
            canvas.drawLine(x, y3, x, mHeight, mPaint3);
        }
        invalidate();
    }

    private void calculatePoints() {
        mContentOneYs = new float[mWidth];
        mContentTwoys = new float[mWidth];
        mContentThreeys = new float[mWidth];
        mRestoreOnes = new float[mWidth];
        mRestoreTwos = new float[mWidth];
        mRestoreThrees = new float[mWidth];
        for (int i = 0; i < mWidth; i++) {
            mContentOneYs[i] = getYPosition(i, SWINGONE, OFFSETONE, INIT_BASE_HEIGHT1);
            mContentTwoys[i] = getYPosition(i, SWINGTWO, OFFSETTWO, INIT_BASE_HEIGHT2);
            mContentThreeys[i] = getYPosition(i, SWINGTHREE, OFFSETTHREE, INIT_BASE_HEIGHT3);
        }
    }


    private void changeRestorePosition() {
        if (mWidth != 0) {
            mPosition1 = (mPosition1 + STEP1) % mWidth;
            System.arraycopy(mContentOneYs, mPosition1, mRestoreOnes, 0, mWidth - mPosition1);
            System.arraycopy(mContentOneYs, 0, mRestoreOnes, mWidth - mPosition1, mPosition1);

            mPosition2 = (mPosition2 + STEP2) % mWidth;
            System.arraycopy(mContentTwoys, mPosition2, mRestoreTwos, 0, mWidth - mPosition2);
            System.arraycopy(mContentTwoys, 0, mRestoreTwos, mWidth - mPosition2, mPosition2);



            mPosition3 = (mPosition3 + STEP3) % mWidth;
            System.arraycopy(mContentThreeys, mPosition3, mRestoreThrees, 0, mWidth - mPosition3);
            System.arraycopy(mContentThreeys, 0, mRestoreThrees, mWidth - mPosition3, mPosition3);

        }
    }




//    mContentOneYs[i] = getYPosition(i, SWINGONE, OFFSETONE, INIT_BASE_HEIGHT1);
    private float getYPosition(int x, int swing, int offset, int baseHeight) {
        float cycle = (float) (2 * Math.PI) / mWidth;
        return (float) Math.sin(cycle * x + offset) * swing + baseHeight;
    }

}
