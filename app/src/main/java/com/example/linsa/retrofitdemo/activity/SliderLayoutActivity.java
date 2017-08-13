package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.linsa.retrofitdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SliderLayoutActivity extends AppCompatActivity {

    @InjectView(R.id.sl_asl_vp)
    SliderLayout slAslVp;


    private String[] mTitle=new String[]{"one" ,"two","three","four","five","six"};
    private String[] mImage={"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"
    ,"https://img3.doubanio.com/img/celebrity/medium/230.jpg"
    ,"https://img1.doubanio.com/img/celebrity/medium/67.jpg"
    ,"https://img3.doubanio.com/img/celebrity/medium/18785.jpg"
    ,"https://img3.doubanio.com/img/celebrity/medium/43502.jpg"
    ,"https://img1.doubanio.com/img/celebrity/medium/49237.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_layout);
        ButterKnife.inject(this);


        initSilerView();




    }

    private void initSilerView() {

        for (int i = 0; i < mTitle.length; i++) {
            TextSliderView textSliderView=new TextSliderView(this);
            textSliderView.description(mTitle[i]);
            textSliderView.image(mImage[i]);
            textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
            slAslVp.addSlider(textSliderView);
        }

        slAslVp.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        slAslVp.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slAslVp.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        slAslVp.setDuration(5000);


    }

    @Override
    protected void onStart() {
        super.onStart();
        slAslVp.startAutoCycle();
    }

    @Override
    protected void onStop() {
        super.onStop();
        slAslVp.stopAutoCycle();

    }
}
