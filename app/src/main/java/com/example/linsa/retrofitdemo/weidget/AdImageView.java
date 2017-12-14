package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Linsa on 2017/12/5:14:05.
 * des:自定义广告的ImageView
 */

public class AdImageView extends AppCompatImageView{
    public AdImageView(Context context) {
        this(context,null);
    }

    public AdImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private int mDx;
    private int mMinDx;

    public void setDx(int dx) {
        if (getDrawable() == null) {
            return;
        }
        mDx = dx - mMinDx;
        if (mDx <= 0) {
            mDx = 0;
        }
        if (mDx > getDrawable().getBounds().height() - mMinDx) {
            mDx = getDrawable().getBounds().height() - mMinDx;
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDx = h;
    }

    public int getDx() {
        return mDx;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        int w = getWidth();
        int h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, w, h);
        canvas.save();
        canvas.translate(0, -getDx());
        super.onDraw(canvas);
        canvas.restore();
    }

    /*
        private RectF mBitmapRectF;
    private Bitmap mBitmap;

    private int mMinDy;



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mMinDy = h;
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        mBitmap = drawableToBitamp(drawable);
        mBitmapRectF = new RectF(0, 0,
                w,
                mBitmap.getHeight() * w / mBitmap.getWidth());

    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    private int mDy;

    public void setDy(int dy) {

        if (getDrawable() == null) {
            return;
        }
        mDy = dy - mMinDy;
        if (mDy <= 0) {
            mDy = 0;
        }
        if (mDy > mBitmapRectF.height() - mMinDy) {
            mDy = (int) (mBitmapRectF.height() - mMinDy);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        canvas.save();
        canvas.translate(0, -mDy);
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        canvas.restore();
    }
     */


}
