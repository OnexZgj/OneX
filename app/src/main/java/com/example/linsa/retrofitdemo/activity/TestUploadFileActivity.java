package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.net.RetrofitHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class TestUploadFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_upload_file);

//
//        RequestBody requestBody = createPartFromString("this is desc!");
//        retrofit2.Call call = RetrofitHelper.getUpLoadFileAPI().uploadMultipleFiles(requestBody,prepareFilePart(new File("/sdcard/ic.jpg"),"photo"),prepareFilePart(new File("/sdcard/img.jpg"),"photo"));
//        call.enqueue(new retrofit2.Callback() {
//            @Override
//            public void onResponse(retrofit2.Call call, retrofit2.Response response) {
//                Toast.makeText(TestUploadFileActivity.this, "上传图片成功!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call call, Throwable t) {
//                Log.d("Tag",t.getMessage().toString());
//                Toast.makeText(TestUploadFileActivity.this, "上传图片失败!", Toast.LENGTH_SHORT).show();
//            }
//        });




        RequestBody requestBody0 = createPartFromString("this is desc!");
        File file=new File("/sdcard/img.jpg");
        File file1=new File("/sdcard/ic.jpg");
        File file2=new File("/sdcard/1.txt");

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);

        Map<String, RequestBody> params=new HashMap<>() ;
        params.put("file\"; filename=\""+ file.getName(), requestBody);
        params.put("file\"; filename=\""+ file1.getName(), requestBody1);
        params.put("file\"; filename=\""+ file2.getName(), requestBody2);

        retrofit2.Call call = RetrofitHelper.getUpLoadFileAPI().uploadMapFile(params);
        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(retrofit2.Call call, retrofit2.Response response) {
                Toast.makeText(TestUploadFileActivity.this, "上传图片成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {
                Log.d("Tag",t.getMessage().toString());
                Toast.makeText(TestUploadFileActivity.this, "上传图片失败!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(File file,String partName) {
//        File file = FileUtils.getFile(this, fileUri);

//        File file = new File(Environment.getExternalStorageDirectory(), "img.jpg");

//        File file=new File("/sdcard/img.jpg");

        // 为file建立RequestBody实例
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
