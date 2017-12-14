package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;

/**
 * Created by Linsa on 2017/11/7:11:14.
 * des: 自定义加载RecycleView的下拉刷新
 */

public class PullToRefreshRecycleView extends RecyclerView {


    /**
     * 下拉刷新
     **/
    private static final int PULL_DOWN = 1;
    /**
     * 松开刷新
     **/
    private static final int RELEASE_REFRESH = 2;
    /**
     * 正在刷新
     **/
    private static final int REFRESHING = 3;
    /**
     * 当前状态
     **/
    private int CUREENTSTATE = PULL_DOWN;
    private LoadingView lvAtlvLoading;
    private TextView tvLoading;
    private LinearLayout llEnd;
    private LinearLayout llWarn;
    private int pulldownHeight;
    private View headerView;
    private float downY = -1;
    private OnRefreshListener mOnRefreshListener;


    public PullToRefreshRecycleView(Context context) {
        this(context, null);
    }

    public PullToRefreshRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public PullToRefreshRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        addHeader();


    }

    private void addHeader() {

        headerView = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_refresh_footer, null);

        lvAtlvLoading = headerView.findViewById(R.id.pb_loading);
        tvLoading = headerView.findViewById(R.id.tv_loading);
        llEnd = headerView.findViewById(R.id.ll_end);
        llWarn = headerView.findViewById(R.id.ll_warn);


        headerView.measure(0, 0);
        pulldownHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -pulldownHeight, 0, 0);
        //初始化头部布局的动画
        initAnimation();

    }

    // 下拉显示刷新头
    // 1.触摸事件
    // 2.下拉操作
    // 3.当前界面显示的第一个条目是listview的第一个条目
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                if (downY == -1) {
                    downY = ev.getY();
                }

                float moveY = ev.getY();
                // 获取移动点和按下点之间的距离
                float distance = moveY - downY;

                //如果正在刷新，则直接结束代码
                if (CUREENTSTATE == REFRESHING) {
                    break;
                }


                // getFirstVisiblePosition() : 获取当前界面显示的第一个条目的位置
                if (distance > 0) {
                    // 计算出空白区域 = 下拉的距离 - 刷新头的高度
                    float paddingTop = distance - pulldownHeight;
                    // 下拉显示刷新头
                    headerView.setPadding(0, (int) paddingTop, 0, 0);

//                    // 1.下拉刷新 -> 松开刷新
//                    if (paddingTop > 0 && CUREENTSTATE == PULL_DOWN) {
//                        CUREENTSTATE = RELEASE_REFRESH;
//                        switchOption();
//                    }
//                    // 2.松开刷新 -> 下拉刷新
//                    if (paddingTop < 0 && CUREENTSTATE == RELEASE_REFRESH) {
//                        CUREENTSTATE = PULL_DOWN;
//                        switchOption();
//                    }

                    // 因为系统的listview是没有下拉空白区域的操作,所以不能使用系统地方listview,如果使用会出空白区域显示问题
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:


                downY = -1;
                if (CUREENTSTATE == REFRESHING) {
                    //正在刷新的操作
                    CUREENTSTATE = REFRESHING;
                    refreshViewState();
                    headerView.setPadding(0, 0, 0, 0);

                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }

                if (CUREENTSTATE == PULL_DOWN) {
                    //设置默认隐藏
                    headerView.setPadding(0, -pulldownHeight, 0, 0);

                }

                break;
        }
        // 因为只有下拉空白区域不需要使用系统的操作,但是下拉其他条目还是需要系统的操作
        return super.onTouchEvent(ev);
    }


    private void refreshViewState() {
        switch (CUREENTSTATE) {
//            跳转到下拉刷新
            case PULL_DOWN:

                break;
//            跳转到释放刷新
            case RELEASE_REFRESH:

                break;
//            跳转到正在刷新
            case REFRESHING:

                break;
        }
    }


    public void onFinishRefresh(boolean isSuccess) {

        headerView.setPadding(0, -pulldownHeight, 0, 0);
        CUREENTSTATE = PULL_DOWN;


    }


    private void initAnimation() {

        lvAtlvLoading.addBitmap(R.mipmap.v4);
        lvAtlvLoading.addBitmap(R.mipmap.v5);
        lvAtlvLoading.addBitmap(R.mipmap.v6);
        lvAtlvLoading.addBitmap(R.mipmap.v7);
        lvAtlvLoading.addBitmap(R.mipmap.v8);
        lvAtlvLoading.addBitmap(R.mipmap.v9);
        lvAtlvLoading.setShadowColor(Color.LTGRAY);
        lvAtlvLoading.setDuration(300);
        lvAtlvLoading.start();
    }


    /**
     * 定义下拉刷新和上拉加载的接口
     */
    public interface OnRefreshListener {
        /**
         * 当下拉刷新时触发此方法
         */
        void onPullDownRefresh();

        /**
         * 当加载更多的时候回调这个方法
         */
        void onLoadingMore();

    }


    public void setOnRefreshListener(PullToRefreshRecycleView.OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }


}
