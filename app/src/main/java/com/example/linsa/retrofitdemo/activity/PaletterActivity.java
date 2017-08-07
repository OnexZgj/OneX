package com.example.linsa.retrofitdemo.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.linsa.retrofitdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PaletterActivity extends AppCompatActivity {

    @InjectView(R.id.btn_ap_gpc)
    Button btnApGpc;
    @InjectView(R.id.iv_ap_source)
    ImageView ivApSource;
    @InjectView(R.id.tv_ap_paletter1)
    TextView tvApPaletter1;
    @InjectView(R.id.tv_ap_paletter2)
    TextView tvApPaletter2;
    @InjectView(R.id.tv_ap_paletter3)
    TextView tvApPaletter3;
    @InjectView(R.id.tv_ap_paletter4)
    TextView tvApPaletter4;
    @InjectView(R.id.tv_ap_paletter5)
    TextView tvApPaletter5;
    @InjectView(R.id.tv_ap_paletter6)
    TextView tvApPaletter6;
    @InjectView(R.id.ll_ap_tv_container)
    LinearLayout llApTvContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paletter);
        ButterKnife.inject(this);


    }

    private List<Palette.Swatch> swatchs = new ArrayList<>();
    private Palette.PaletteAsyncListener listener = new Palette.PaletteAsyncListener() {
        @Override
        public void onGenerated(Palette palette) {
            if (palette != null) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力的
                Palette.Swatch vibrantDark = palette.getDarkVibrantSwatch();//有活力的，暗色
                Palette.Swatch vibrantLight = palette.getLightVibrantSwatch();//有活力的，亮色
                Palette.Swatch muted = palette.getMutedSwatch();//柔和的
                Palette.Swatch mutedDark = palette.getDarkMutedSwatch();//柔和的，暗色
                Palette.Swatch mutedLight = palette.getLightMutedSwatch();//柔和的,亮色
                swatchs.clear();

                swatchs.add(vibrant);
                swatchs.add(vibrantDark);
                swatchs.add(vibrantLight);
                swatchs.add(muted);
                swatchs.add(mutedDark);
                swatchs.add(mutedLight);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        show();
                    }
                });
            }
        }
    };

    /**
     * 进行展示获取出来的颜色
     */
    private void show() {
        for (int i = 0; i < swatchs.size(); i++) {
            if (swatchs.get(i)!=null){
                System.out.println(i+"   ------    xx");
                tvApPaletter1.setBackgroundColor(swatchs.get(i).getRgb());
            }
        }


//        swatch.getPopulation(): 样本中的像素数量
//        swatch.getRgb(): 颜色的RBG值
//        swatch.getHsl(): 颜色的HSL值
//        swatch.getBodyTextColor(): 主体文字的颜色值
//        swatch.getTitleTextColor(): 标题文字的颜色值


        tvApPaletter1.setBackgroundColor(swatchs.get(0).getRgb());
        tvApPaletter2.setBackgroundColor(swatchs.get(1).getRgb());
        tvApPaletter3.setBackgroundColor(swatchs.get(2).getRgb());
        tvApPaletter4.setBackgroundColor(swatchs.get(3).getRgb());
        tvApPaletter5.setBackgroundColor(swatchs.get(4).getRgb());
        tvApPaletter6.setBackgroundColor(swatchs.get(5).getRgb());
    }

    @OnClick({R.id.btn_ap_gpc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ap_gpc:
                Bitmap bitmap = ((BitmapDrawable) ivApSource.getDrawable()).getBitmap();
                if (bitmap == null) {
                    return;
                }

                //进行获取颜色
                Palette.from(bitmap).generate(listener);


                break;
        }
    }
}
