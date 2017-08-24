package com.example.linsa.retrofitdemo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.bean.Movie;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Linsa on 2017/8/23:15:19.
 * des:
 */

public class SwipeCardAdapter extends BaseAdapter {


    private List<Movie.SubjectsBean> mListData;

    public SwipeCardAdapter(List<Movie.SubjectsBean> mSubjects) {
        this.mListData = mSubjects;

    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Movie.SubjectsBean getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_swipe_card, null);
            holder = new ViewHolder(convertView);

            holder.ivIscMovieIcon = (ImageView) convertView.findViewById(R.id.iv_isc_movie_icon);
            holder.tvIscName = (TextView) convertView.findViewById(R.id.tv_isc_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvIscName.setText(getItem(position).getTitle());
        Glide.with(parent.getContext()).load(getItem(position).getCasts().get(0).getAvatars().getLarge())
                .into(holder.ivIscMovieIcon);
        return convertView;
    }


    class ViewHolder {
        @InjectView(R.id.iv_isc_movie_icon)
        ImageView ivIscMovieIcon;
        @InjectView(R.id.tv_isc_name)
        TextView tvIscName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
