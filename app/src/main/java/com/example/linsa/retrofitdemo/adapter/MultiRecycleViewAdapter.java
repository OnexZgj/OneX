package com.example.linsa.retrofitdemo.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.bean.MovieDirector;
import com.example.linsa.retrofitdemo.util.Common;

import java.util.List;

/**
 * Created by Linsa on 2017/8/17:17:10.
 * des:
 */

public class MultiRecycleViewAdapter extends BaseMultiItemQuickAdapter<MovieDirector,BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultiRecycleViewAdapter(List<MovieDirector> data) {
        super(data);
        addItemType(Common.MOVIE_DIRECT, R.layout.item_base_multi_adapter);
        addItemType(Common.MOVIE, R.layout.item_base_quick_adapter);

    }

    @Override
    protected void convert(BaseViewHolder helper, MovieDirector item) {
        switch (helper.getItemViewType()){
            case Common.MOVIE:
                helper.setText(R.id.tv_ibqa_name,item.name)
                        .setText(R.id.tv_ibqa_title,item.age);
                break;
            case Common.MOVIE_DIRECT:
                helper.setText(R.id.tv_ibma_name,item.name)
                        .setText(R.id.tv_ibma_title,item.age);
                break;
        }
    }
}
