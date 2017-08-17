package com.example.linsa.retrofitdemo.weidget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

/**
 * Created by Linsa on 2017/8/7:16:38.
 * des:
 */

public class DampingScrollView extends ScrollView {

    /**
     * 滑动方向向上
     */
    private static final int DIRECTION_UP = -1;
    /**
     * 滑动方向向下
     */
    private static final int DIRECTION_DOWN = 1;

    /**
     * 滑动要开始的那个距离，大于该距离，才开始滑动
     */
    private int mTouchSlop;

    private float mScale;

    /**
     * 是否在拖动的状态
     */
    private boolean isBeingDragged;


    /**
     * 正在恢复动画，从拉伸到正常状态
     */
    private boolean isRestoring;
    /**
     * 第一个接触的触摸点
     */
    private int mActivePointerId;

    /**
     * 供全局使用的最开始的Y的方向
     */
    private float mInitialMotionY;


    /**
     * 滑动的距离
     */
    private float mMoveInstance;

    public DampingScrollView(Context context) {
        this(context, null);
    }

    public DampingScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DampingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DampingScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        //需要拦截的情况：

        switch (action) {


            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                isBeingDragged = false;
                //手指点击的距离
                float initialMotionY = getMotionEventY(event);
                if (initialMotionY == -1) {
                    super.onInterceptTouchEvent(event);
                }

                mInitialMotionY = initialMotionY;
                break;

            case MotionEvent.ACTION_MOVE:

                float moveY = getMotionEventY(event);
                //1、滑动到顶部，继续向上滑动
                if (isScrollToTop() && !isScrollToBottom()) {
                    float moveDistance = moveY - mInitialMotionY;
                    if (moveDistance > mTouchSlop && !isBeingDragged) {
                        //应该拦截
                        isBeingDragged = true;
                    }
                } else if (isScrollToBottom() && !isScrollToTop()) {
                    //2、滑动到底部，继续想下滑动

                    float moveDistance = mInitialMotionY - moveY;
                    if (moveDistance > mTouchSlop && !isBeingDragged) {
                        //应该拦截
                        isBeingDragged = true;
                    }
                } else if (isScrollToTop() && isScrollToBottom()) {
                    //3、没有占满屏，则也需要进行拦截,既是顶部也是底部
                    float moveDistance = moveY - mInitialMotionY;
                    if (Math.abs(moveDistance) > mTouchSlop && !isBeingDragged) {
                        isBeingDragged = true;
                    }

                } else {
                    //其他情况，父类处理
                    super.onInterceptTouchEvent(event);
                }
                break;

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                isBeingDragged = false;
                break;
        }

        //让是否在拖动与父类的事件进行或运算
        return isBeingDragged || super.onInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                isBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:

                float moveY = getMotionEventY(event);
                if (isScrollToTop() && !isScrollToBottom()) {
                    mMoveInstance = moveY - mInitialMotionY;
                    if (mMoveInstance < 0) {
                        return super.onTouchEvent(event);
                    }
                    mScale = calculateRate(mMoveInstance);
                    pull(mScale);
                    return true;
                } else if (isScrollToBottom() && !isScrollToTop()) {
                    mMoveInstance=mInitialMotionY-moveY;
                    if (mMoveInstance<0){
                        return super.onTouchEvent(event);
                    }
                    mScale = calculateRate(mMoveInstance);
                    push(mScale);
                    return true;
                }else if (isScrollToTop() && isScrollToBottom()){
                    // 在底部也在顶部
                    mMoveInstance = moveY - mInitialMotionY;
                    if (mMoveInstance > 0) {
                        mScale = calculateRate(mMoveInstance);
                        pull(mScale);
                    } else {
                        mScale = calculateRate(-mMoveInstance);
                        push(mScale);
                    }
                    return true;
                }else{
                    return super.onTouchEvent(event);
                }


            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isScrollToTop() && !isScrollToBottom()) {
                    animateRestore(true);
                } else if (!isScrollToTop() && isScrollToBottom()) {
                    animateRestore(false);
                } else if (isScrollToTop() && isScrollToBottom()) {
                    if (mMoveInstance > 0) {
                        animateRestore(true);
                    } else {
                        animateRestore(false);
                    }
                } else {
                    return super.onTouchEvent(event);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    private void onSecondaryPointerUp(MotionEvent event) {
        final int pointerIndex = MotionEventCompat.getActionIndex(event);
        final int pointerId = event.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = event.getPointerId(newPointerIndex);
        }
    }


    private float getMotionEventY(MotionEvent event) {
        int index = event.findPointerIndex(mActivePointerId);
        return index < 0 ? -1f : event.getY(index);
    }


    /**
     * 是否滑动到顶部
     *
     * @return
     */
    private boolean isScrollToTop() {
        return !ViewCompat.canScrollVertically(this, -1);
    }


    /**
     * 是否滑动到底部
     *
     * @return
     */
    private boolean isScrollToBottom() {
        return !ViewCompat.canScrollVertically(this, 1);
    }


    /**
     * 拉伸，顶部向下则是拉伸
     *
     * @param scale
     */
    private void pull(float scale) {
        //设置锚点，缩放开始的地方
        this.setPivotY(0);
        this.setScaleY(scale);
    }

    /**
     * 向上推
     *
     * @param scale
     */
    private void push(float scale) {
        this.setPivotY(this.getHeight());
        this.setScaleY(scale);
    }


    /**
     * 计算缩放比
     *
     * @param distance 滑动的距离
     * @return
     */
    private float calculateRate(float distance) {
        float originalDragPercent = distance / (getResources().getDisplayMetrics().heightPixels);
        float dragPercent = Math.min(1f, originalDragPercent);
        float rate = 2f * dragPercent - (float) Math.pow(dragPercent, 2f);
        return 1 + rate / 5f;
    }


    private void animateRestore(final boolean isPullRestore) {
        ValueAnimator animator = ValueAnimator.ofFloat(mScale, 1f);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator(2f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (isPullRestore) {
                    pull(value);
                } else {
                    push(value);
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isRestoring = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isRestoring = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }


}
