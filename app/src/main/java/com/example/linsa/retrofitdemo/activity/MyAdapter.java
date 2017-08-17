package com.example.linsa.retrofitdemo.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;

import java.util.ArrayList;

/**
 * Created by Linsa on 2017/8/14:14:29.
 * des:RecycleViewAdapter的使用
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> datas;

    public MyAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_rv_slding, null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position));
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_irs_des);
        }
    }




}
