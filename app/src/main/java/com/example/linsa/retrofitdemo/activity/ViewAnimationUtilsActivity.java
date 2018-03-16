package com.example.linsa.retrofitdemo.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.linsa.retrofitdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * create by onex on 2017/12/14
 */
public class ViewAnimationUtilsActivity extends AppCompatActivity {

    @InjectView(R.id.btn_avau_left_top)
    Button btnAvauLeftTop;
    @InjectView(R.id.btn_avau_right_top)
    Button btnAvauRightTop;
    @InjectView(R.id.btn_avau_center)
    Button btnAvauCenter;
    @InjectView(R.id.btn_avau_bottom)
    Button btnAvauBottom;
    @InjectView(R.id.iv_avau_img)
    ImageView ivAvauImg;
    @InjectView(R.id.iv_avau_icon)
    ImageView ivAvauIcon;
    private int imgWidth;
    private int imgHeight;
    private int mSceenHeight;
    private int mSceenWidth;
    private int btnHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation_utils);
        ButterKnife.inject(this);

        imgWidth = ivAvauImg.getWidth();
        imgHeight = ivAvauImg.getHeight();
        mSceenHeight = getResources().getDisplayMetrics().heightPixels;
        mSceenWidth = getResources().getDisplayMetrics().widthPixels;
        btnHeight = btnAvauBottom.getHeight();

    }

    @OnClick({R.id.btn_avau_left_top, R.id.btn_avau_right_top, R.id.btn_avau_center, R.id.btn_avau_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_avau_left_top:
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(ivAvauImg, 0, btnHeight, 0, mSceenHeight);
                circularReveal.setDuration(2000);

                circularReveal.start();
                break;
            case R.id.btn_avau_right_top:
                Animator animator = ViewAnimationUtils.createCircularReveal(ivAvauImg, mSceenWidth, btnHeight, 0, mSceenHeight);
                animator.setDuration(2000);
                animator.start();
                break;
            case R.id.btn_avau_center:

                Animator animator2 = ViewAnimationUtils.createCircularReveal(ivAvauImg, mSceenWidth / 2, mSceenHeight / 2, mSceenHeight, 80);
                animator2.setDuration(5000);
                animator2.start();
                break;
            case R.id.btn_avau_bottom:
                final Animator animator3 = ViewAnimationUtils.createCircularReveal(ivAvauImg, mSceenWidth / 2, mSceenHeight-3*btnHeight, mSceenHeight, 80);
                animator3.setDuration(2000);
                animator3.start();

                animator3.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {


                        ivAvauImg.setVisibility(View.GONE);
                        ivAvauIcon.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
        }
    }
}
