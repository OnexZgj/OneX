package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.linsa.retrofitdemo.R;

/**
 * Created by Linsa on 2017/11/6:15:05.
 * des:
 */

public class LoadMoreRecycleView extends RecyclerView {
    private View footerView;
    private int footermeasuredHeight;

    public LoadMoreRecycleView(Context context) {
        this(context, null);
    }

    public LoadMoreRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadMoreRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addFooter();
    }

    private void addFooter() {
        footerView = View.inflate(getContext(), R.layout.listview_footer, null);

        //隐藏底部条目
        footerView.measure(0, 0);
        footermeasuredHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, 0, 0, -footermeasuredHeight);

        addFooterView(footerView);//添加listview的底部条目
    }

    private void addFooterView(View footerView) {


    }

}
