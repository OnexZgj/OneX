package com.example.linsa.retrofitdemo.netprogress;

/**
 * Created by Linsa on 2018/3/19:16:08.
 * des:
 */

public interface ProgressListener {
    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
