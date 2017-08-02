package com.example.linsa.retrofitdemo;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.linsa.retrofitdemo.service.VideoLiveWallpaper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LiveWallPaper extends AppCompatActivity {

    @InjectView(R.id.btn_alwp_set_papaer)
    Button btnAlwpSetPapaer;
    @InjectView(R.id.cb_alwp_quite)
    CheckBox cbAlwpQuite;
    @InjectView(R.id.cb_alwp_normal)
    CheckBox cbAlwpNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_wall_paper);
        ButterKnife.inject(this);

//        cbAlwpQuite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    //静音
//                    VideoLiveWallpaper.voiceNormal(LiveWallPaper.this);
//                }else{
//                    //开启
//                    VideoLiveWallpaper.voiceSilence(LiveWallPaper.this);
//                }
//            }
//        });


    }

    @OnClick({R.id.btn_alwp_set_papaer, R.id.cb_alwp_quite, R.id.cb_alwp_normal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_alwp_set_papaer:
                Intent intent =new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(LiveWallPaper.this, VideoLiveWallpaper.class));
                startActivity(intent);


                break;
            case R.id.cb_alwp_quite:
                break;
            case R.id.cb_alwp_normal:
                break;
        }
    }
}
