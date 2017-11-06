package com.example.linsa.retrofitdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;

import java.util.List;

/**
 * Created by Linsa on 2017/11/6:10:37.
 * des: 上拉加载更多的adapter
 */

public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //TODO 用父类接受参数更好一点

    private List<String> dataList;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;


    public LoadMoreAdapter(List<String> dataList) {
        this.dataList = dataList;
    }



    @Override
    public int getItemCount() {
        //数据加1
        return dataList.size()+1;
    }


    @Override
    public int getItemViewType(int position) {

        //TODO 这里需要重点理解
        if (position+1==getItemCount()){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_recyclerview, parent, false);
            return new RecyclerViewHolder(view);
        }else if (viewType==TYPE_FOOTER){

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //正常布局的holder
        if (holder instanceof RecyclerViewHolder){
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            recyclerViewHolder.tvItem.setText(dataList.get(position));
        }else if (holder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) holder;

            switch (loadState){
                case  LOADING:
                    //正在加载中
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    footViewHolder.llWarn.setVisibility(View.GONE);

                    break;
                case LOADING_COMPLETE:
                    //加载完成，还有数据
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    footViewHolder.llWarn.setVisibility(View.VISIBLE);

                    break;
                case LOADING_END:
                    //没有数据
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    footViewHolder.llWarn.setVisibility(View.GONE);
                    break;
            }
        }
    }



    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }



    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }


    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;
        LinearLayout llWarn;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
            llWarn = (LinearLayout) itemView.findViewById(R.id.ll_warn);
        }
    }



}
