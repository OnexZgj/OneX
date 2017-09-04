package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.net.RetrofitHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.provider.CallLog.Calls.TYPE;


public class UpLoadFileActivity extends AppCompatActivity {

    @InjectView(R.id.btn_aulf_upload_file)
    Button btnAulfUploadFile;
    @InjectView(R.id.btn_aulf_ok_upload)
    Button btnAulfOkUpload;
    @InjectView(R.id.pb_aulf_upload)
    ProgressBar pbAulfUpload;
    @InjectView(R.id.btn_aulf_retrofit_upload)
    Button btnAulfRetrofitUpload;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_file);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.btn_aulf_retrofit_upload,R.id.btn_aulf_upload_file, R.id.btn_aulf_ok_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_aulf_upload_file:
//                String path = Environment.getExternalStorageDirectory()+File.separator+"img.jpg";
//
//                File file = new File(path);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
//                RequestBody requestFile =
//                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//                MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);
//
//                String descriptionString = "This is a description";
//                RequestBody description =
//                        RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
//
//
//                RetrofitHelper.getUpLoadFileAPI().upload(description,body)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<ResponseBody>() {
//                            @Override
//                            public void onCompleted() {
//                                Toast.makeText(UpLoadFileActivity.this, "upload success!", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Toast.makeText(UpLoadFileActivity.this, "upload fail !", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onNext(ResponseBody responseBody) {
//
//                            }
//                        });
//
                File img = new File(Environment.getExternalStorageDirectory(), "img.jpg");
                File fileimg2 = new File(Environment.getExternalStorageDirectory(), "img2.jpg");
                File fileimg3 = new File(Environment.getExternalStorageDirectory(), "img3.jpg");


                List<File> list = new ArrayList<>();
                list.add(img);
                list.add(fileimg2);
                list.add(fileimg3);

                OkGo.post("http://192.168.1.183:8080/UpLoadFile/servlet/UploadHtmlServlet")
                        .isMultipart(true)
                        .addFileParams("file", list)
                        .execute(new com.lzy.okgo.callback.Callback<Object>() {
                            @Override
                            public Object convertResponse(Response response) throws Throwable {
                                return null;
                            }

                            @Override
                            public void onStart(com.lzy.okgo.request.base.Request<Object, ? extends com.lzy.okgo.request.base.Request> request) {

                            }

                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<Object> response) {
                                Toast.makeText(UpLoadFileActivity.this, "success  upload", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCacheSuccess(com.lzy.okgo.model.Response<Object> response) {

                            }

                            @Override
                            public void onError(com.lzy.okgo.model.Response<Object> response) {
                                Toast.makeText(UpLoadFileActivity.this, "faild upload", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFinish() {

                            }

                            @Override
                            public void uploadProgress(Progress progress) {
                                pbAulfUpload.setProgress((int) progress.fraction*100);
                            }

                            @Override
                            public void downloadProgress(Progress progress) {

                            }
                        });


                break;
            case R.id.btn_aulf_ok_upload:


                client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();


                File fileimg = new File(Environment.getExternalStorageDirectory(), "img2.jpg");
                if (!fileimg.exists()) {
                    Toast.makeText(UpLoadFileActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                } else {
                    RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), fileimg);
                    RequestBody requestBody = new MultipartBody.Builder().addFormDataPart("filename", fileimg.getName(), fileBody).build();

                    final Request requestPostFile = new Request.Builder()
                            .url("http://192.168.1.183:8080/UpLoadFile/servlet/UploadHtmlServlet")
                            .post(requestBody)
                            .build();


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client.newCall(requestPostFile).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UpLoadFileActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UpLoadFileActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                        }
                    }).start();

                }

                break;

            case R.id.btn_aulf_retrofit_upload:

                File file = new File(Environment.getExternalStorageDirectory(), "img.jpg");
                RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
                MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", "icon.png", photoRequestBody);

                retrofit2.Call call = RetrofitHelper.getUpLoadFileAPI().upLoadFile(photo);
                call.enqueue(new retrofit2.Callback() {
                    @Override
                    public void onResponse(retrofit2.Call call, retrofit2.Response response) {
                        Toast.makeText(UpLoadFileActivity.this, "上传图片成功!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call call, Throwable t) {
                        Toast.makeText(UpLoadFileActivity.this, "上传图片失败!", Toast.LENGTH_SHORT).show();
                    }
                });


                break;
        }
    }

}
