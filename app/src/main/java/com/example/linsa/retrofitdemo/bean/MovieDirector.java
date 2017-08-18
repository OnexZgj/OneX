package com.example.linsa.retrofitdemo.bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Linsa on 2017/8/17:17:14.
 * des:
 */

public class MovieDirector implements MultiItemEntity {

    @Override
    public int getItemType() {
        return type;
    }


    public int type;
    public String name;
    public String age;

}
