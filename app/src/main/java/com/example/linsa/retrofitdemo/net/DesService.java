package com.example.linsa.retrofitdemo.net;

import com.example.linsa.retrofitdemo.bean.DesContent;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Linsa on 2017/9/21:16:56.
 * des:测试own 实现的接口
 */

public interface DesService {

    @GET("httpFuckServlet")
    Observable<DesContent> getDesContent();
}
