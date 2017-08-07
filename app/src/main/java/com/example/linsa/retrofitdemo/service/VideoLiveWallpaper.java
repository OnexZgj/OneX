package com.example.linsa.retrofitdemo.service;

import android.content.BroadcastReceiver;
import android.media.MediaPlayer;
import android.os.Environment;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Linsa on 2017/8/2:10:22.
 * des:
 */

public class VideoLiveWallpaper extends WallpaperService {

    public static final String VIDEO_PARAMS_CONTROL_ACTION = "com.zgj.livewallpaper";
    public static final String KEY_ACTION = "action";
    public static final int ACTION_VOICE_SILENCE = 110;
    public static final int ACTION_VOICE_NORMAL = 111;

    @Override
    public Engine onCreateEngine() {
        return new VideoEngine();
    }

//    public static void voiceSilence(Context context) {
//        Intent intent = new Intent(VideoLiveWallpaper.VIDEO_PARAMS_CONTROL_ACTION);
//        intent.putExtra(VideoLiveWallpaper.KEY_ACTION, VideoLiveWallpaper.ACTION_VOICE_SILENCE);
//        context.sendBroadcast(intent);
//    }
//
//    public static void voiceNormal(Context context) {
//        Intent intent = new Intent(VideoLiveWallpaper.VIDEO_PARAMS_CONTROL_ACTION);
//        intent.putExtra(VideoLiveWallpaper.KEY_ACTION, VideoLiveWallpaper.ACTION_VOICE_NORMAL);
//        context.sendBroadcast(intent);
//    }
    private BroadcastReceiver mVideoParamsControlReceiver;
    private MediaPlayer mMediaPlayer;

    private class VideoEngine extends Engine {



//        @Override
//        public void onCreate(SurfaceHolder surfaceHolder) {
//            super.onCreate(surfaceHolder);
//            IntentFilter intentFilter = new IntentFilter(VIDEO_PARAMS_CONTROL_ACTION);
//            registerReceiver(mVideoParamsControlReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    int action = intent.getIntExtra(KEY_ACTION, -1);
//
//                    switch (action) {
//                        case ACTION_VOICE_NORMAL:
//                            mMediaPlayer.setVolume(1.0f, 1.0f);
//                            break;
//                        case ACTION_VOICE_SILENCE:
//                            mMediaPlayer.setVolume(0, 0);
//                            break;
//                    }
//                }
//            }, intentFilter);
//        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mMediaPlayer=new MediaPlayer();
            mMediaPlayer.setSurface(holder.getSurface());

            try {
//                AssetFileDescriptor assetFileDescriptor = getApplicationContext().getAssets().openFd(("huan.mp4"));

                mMediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/onex.mp4");
//                mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());

                mMediaPlayer.setLooping(true);
                mMediaPlayer.setVolume(0,0);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible){
                //进行开启surfaceView

                if(mMediaPlayer!=null) {
                    mMediaPlayer.start();
                }
            }else{
                //进行暂停surfaceView
                if (mMediaPlayer!=null) {
                    mMediaPlayer.pause();
                }
            }

        }



        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mMediaPlayer.release();
            mMediaPlayer=null;
        }

    }

//    @Override
//    public void onDestroy() {
//        unregisterReceiver(mVideoParamsControlReceiver);
//        super.onDestroy();
//    }
}
