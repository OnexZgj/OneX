package com.example.linsa.retrofitdemo.presenter;

import android.content.Context;

/**
 * Created by Linsa on 2017/7/28:14:19.
 * des: MainActivity的主要变现层
 */

public interface IMainPresenter {

    void getMovieThird();
    void imgblur();
    void startLogin(Context context ,Class clazz);
    void startDragGridView();

}
