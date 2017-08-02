package com.example.linsa.retrofitdemo.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.linsa.retrofitdemo.view.IMainActivity;

/**
 * Created by Linsa on 2017/7/28:14:22.
 * des:变现层具体的实现逻辑
 */

public class MainPresenter implements IMainPresenter {

    private IMainActivity iMainActivity;

    public MainPresenter(IMainActivity iMainActivity) {


        this.iMainActivity = iMainActivity;
    }

    @Override
    public void getMovieThird() {
//        iMainActivity.getMovieThird();
    }

    @Override
    public void imgblur() {
//        iMainActivity.imgblur();
    }

    @Override
    public void startLogin(Context context, Class clazz) {
        context.startActivity(new Intent(context,clazz));
    }



    @Override
    public void startDragGridView() {
//        iMainActivity.startDragGridView();
    }
}
