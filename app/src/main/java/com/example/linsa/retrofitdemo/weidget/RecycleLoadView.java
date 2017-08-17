package com.example.linsa.retrofitdemo.weidget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.example.linsa.retrofitdemo.R;

/**
 * Created by Linsa on 2017/8/17:15:29.
 * des:recycleView的加载更多的自定义LoadingView
 */

public class RecycleLoadView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.statelayout;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_state;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_state;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_state;
    }

}
