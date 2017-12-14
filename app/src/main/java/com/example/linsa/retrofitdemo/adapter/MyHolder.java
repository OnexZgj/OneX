package com.example.linsa.retrofitdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;

/**
 * Created by Linsa on 2017/9/18:10:20.
 * des:
 */

class MyHolder extends RecyclerView.ViewHolder {

    public TextView mtvTextView;

    public MyHolder(View itemView) {
        super(itemView);
        mtvTextView = itemView.findViewById(R.id.tv_text);
    }


}
