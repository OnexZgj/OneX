package com.example.linsa.retrofitdemo.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Linsa on 2017/9/2:14:43.
 * des: 进行文件上传的接口
 */

public interface UpLoadFileService {

//    @Multipart
//    @POST("uploadFile")
//    Observable<ResponseBody> upload(@Part("description")RequestBody description, @Part MultipartBody.Part file);


    //Retrofit单文件上传
    @Multipart
    @POST("UploadHtmlServlet")
    Call<ResponseBody> upLoadFile(@Part MultipartBody.Part file);



    //Retrofit进行多文件上传
    @Multipart
    @POST("UploadHtmlServlet")
    Call<ResponseBody> upLoadMultFile(@PartMap Map<String, RequestBody> filesMap);


    //Retrofit单文件
    @Multipart
    @POST("UploadServlet")
    Call<ResponseBody> upLoadFile( @Part("description") RequestBody description,@Part MultipartBody.Part file);


    @Multipart
    @POST("UploadServlet")
    Call<ResponseBody> uploadMultipleFiles(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2);



    @Multipart
    @POST("UploadServlet")
    Call<ResponseBody> uploadMapFile(@PartMap Map<String, RequestBody> params);






}
