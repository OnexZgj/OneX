package com.example.linsa.retrofitdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.linsa.retrofitdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OptionsCompatActivity extends AppCompatActivity {

    @InjectView(R.id.iv_aoc_img1)
    ImageView ivAocImg1;
    @InjectView(R.id.btn_aoc_start)
    Button btnAocStart;
    @InjectView(R.id.iv_aoc_img2)
    ImageView ivAocImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //----从上到下有个滑动的效果
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Transition slide_top = TransitionInflater.from(this).inflateTransition(android.R.transition.slide_top);
//        getWindow().setEnterTransition(slide_top);


        //----淡入淡出，没什么效果
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Transition fade = TransitionInflater.from(this).inflateTransition(android.R.transition.fade);
//        getWindow().setEnterTransition(fade);


        //----略带卡顿---
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Transition fade = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
//        getWindow().setEnterTransition(fade);


        //----从上从下进行进入Activity---
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition fade = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);
        getWindow().setEnterTransition(fade);


        setContentView(R.layout.activity_options_compat);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_aoc_start)
    public void onViewClicked() {

        Intent intent = new Intent(OptionsCompatActivity.this, ShareElementActivity.class);

//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OptionsCompatActivity.this, ivAocImg1, "taylor").toBundle());

        //当需要使用多个动画的切换的时候，就需要使用到Pair对象，ActivityOptionsCompat中可以使用不限个数的Pair
        Pair taylor = new Pair<>(ivAocImg1, ViewCompat.getTransitionName(ivAocImg1));
        Pair zhangl = new Pair<>(ivAocImg2, ViewCompat.getTransitionName(ivAocImg2));

        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, taylor, zhangl);

        //----当使用多个图片文件的时候就会出现卡顿的效果
        ActivityCompat.startActivity(this,intent,transitionActivityOptions.toBundle());


    }
}
