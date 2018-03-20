package com.example.linsa.retrofitdemo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.net.DownFileService;
import com.example.linsa.retrofitdemo.net.HttpMethods;
import com.example.linsa.retrofitdemo.net.RetrofitHelper;
import com.example.linsa.retrofitdemo.netprogress.DownloadProgressHandler;
import com.example.linsa.retrofitdemo.netprogress.ProgressHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestUploadFileActivity extends AppCompatActivity {

    private String TAG = "retrofit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_upload_file);

        final Button btnDownload = (Button) findViewById(R.id.btn_download);
        final Button btnDownloadProgress = (Button) findViewById(R.id.btn_download_progress);


        btnDownloadProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitDownload();
            }
        });


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = RetrofitHelper.getDownloadApi().downloadFile();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                        btnDownload.setText(writtenToDisk ? "success" : "false");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(TestUploadFileActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        final RequestBody requestBody = createPartFromString("this is desc!");
        retrofit2.Call call = RetrofitHelper.getUpLoadFileAPI().upLoadPrefectFile(requestBody, prepareFilePart(new File("/sdcard/1.zip"), "file"));
        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(retrofit2.Call call, retrofit2.Response response) {

                String s = response.body().toString();
                String s1 = response.message().toString();
                Log.d("TAG", "onResponse: " + s1);
                Toast.makeText(TestUploadFileActivity.this, "上传图片成功!" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {

                String s = t.getMessage().toString();
                Log.d(TAG, s);
                Toast.makeText(TestUploadFileActivity.this, "上传图片失败!" + s, Toast.LENGTH_SHORT).show();
            }
        });


//        RequestBody requestBody0 = createPartFromString("this is desc!");
//        File file=new File("/sdcard/img.jpg");
//        File file1=new File("/sdcard/ic.jpg");
//        File file2=new File("/sdcard/1.txt");
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
//        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
//
//        Map<String, RequestBody> params=new HashMap<>() ;
//        params.put("file\"; filename=\""+ file.getName(), requestBody);
//        params.put("file\"; filename=\""+ file1.getName(), requestBody1);
//        params.put("file\"; filename=\""+ file2.getName(), requestBody2);
//
//        retrofit2.Call call = RetrofitHelper.getUpLoadFileAPI().uploadMapFile(params);
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


    }


    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(File file, String partName) {
//        File file = FileUtils.getFile(this, fileUri);

//        File file = new File(Environment.getExternalStorageDirectory(), "img.jpg");

//        File file=new File("/sdcard/img.jpg");

        // 为file建立RequestBody实例
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + File.separator + "taylor.png");

            Log.d(TAG, "writeResponseBodyToDisk: " + Environment.getExternalStorageDirectory().getAbsolutePath());

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


    private void retrofitDownload(){
        //监听下载进度
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpMethods.BASE_URL4);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        DownFileService retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(DownFileService.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                Log.e("onProgress",String.format("%d%% done\n",(100 * bytesRead) / contentLength));
                Log.e("done","--->" + String.valueOf(done));
                dialog.setMax((int) (contentLength/1024));
                dialog.setProgress((int) (bytesRead/1024));

                if(done){
                    dialog.dismiss();
                }
            }
        });

        Call<ResponseBody> call = retrofit.downLoadFileProgress();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(Environment.getExternalStorageDirectory(), "12345.apk");
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
