package com.example.linsa.retrofitdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.linsa.retrofitdemo.R;

import java.util.ArrayList;

/**
 * Created by Linsa on 2017/9/18:10:12.
 * des:
 */

public class NormalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> mData;

    public NormalAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);

        MyHolder holder = new MyHolder(inflate);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder hold = (MyHolder) holder;
            ((MyHolder) holder).mtvTextView.setText(mData.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
