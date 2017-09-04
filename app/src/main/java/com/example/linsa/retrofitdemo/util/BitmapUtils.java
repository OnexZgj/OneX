package com.example.linsa.retrofitdemo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Linsa on 2017/8/29:11:41.
 * des:图片压缩处理的方法
 */

public class BitmapUtils {

    /**
     * 压缩bitmap图片到特定的大小
     *
     * @param bitmap   要压缩的图片
     * @param fileSize 要压缩的大小
     * @return 压缩后的图片
     */
    public static Bitmap compressBitmapBySizw(Bitmap bitmap, int fileSize) {
        //核心思想，使用内存流，先读取到内存中
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);

        //判断这个内存流的大小是否是指定的大小
        while (bos.toByteArray().length / 1024 > fileSize) {
            //清空流，重新进行
            bos.reset();
            quality -= 20;
            if (quality<0){
                quality=1;
                bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
                break;
            }
            //如果不是，通过设置option的大小，重新压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }

        //将压缩后的文件输出流使用内存流生成一个bitmap对象
        ByteArrayInputStream bis=new ByteArrayInputStream(bos.toByteArray());
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=false;
        //设置采样率
        options.inSampleSize=1;
        //设置色彩模式
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        Bitmap compressBitmap = BitmapFactory.decodeStream(bis, null, options);
        //如果到达这个大小，则进行通过内存输出流，重新设置生成一个bitmap文件对象
        return compressBitmap;
    }

}
