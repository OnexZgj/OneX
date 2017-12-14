package com.example.linsa.retrofitdemo.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Linsa on 2017/11/7:16:25.
 * des:
 */

public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;


    /**
     * 接受外部出来的Adapter
     */
    private final RecyclerView.Adapter mInnerAdapter;


    /**
     * 接受多个HeadView的容器,相当于Map,优于Map,因为key只能是int类型
     */
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();

    /**
     * 接受多个FooterView的容器,SparseArrayCompat有什么特点呢？它类似于Map，只不过在某些情况下比Map的性能要好，并且只能存储key为int的情况
     * 第一个headerView对应的是BASE_ITEM_TYPE_HEADER，第二个对应的是BASE_ITEM_TYPE_HEADER+1；
     */
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();



    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter)
    {
        mInnerAdapter = adapter;
    }


    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }


    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }


    public void addHeaderView(View view)
    {
        //加入一个View则进行加一,巧妙的通过容器的大小控制
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view)
    {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //TODO  实现难点
        if (mHeaderViews.get(viewType)!=null){
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return holder;
        }

        if (mFootViews.get(viewType)!=null){
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }



        return mInnerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (isHeaderViewPos(position)){
            return;
        }

        if (isFooterViewPos(position)){
            return;
        }

        //正常显示的item的position,应该是减去HeadView的Count
        mInnerAdapter.onBindViewHolder(holder,position-getHeadersCount());

    }


    @Override
    public int getItemViewType(int position) {

        if (isHeaderViewPos(position)){
            return mHeaderViews.keyAt(position);
        }

        if (isFooterViewPos(position)){
            return mFootViews.keyAt(position-getRealItemCount()-getHeadersCount());
        }

        return super.getItemViewType(position-getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getRealItemCount()+getHeadersCount()+getFootersCount();
    }

    /**
     * 获得HeadView的Count
     * @return
     */
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    /**
     * 获得ItemView的Count
     * @return
     */
    public int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    public int getFootersCount()
    {
        return mFootViews.size();
    }

}
