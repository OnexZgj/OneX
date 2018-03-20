package com.example.linsa.retrofitdemo.netprogress;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Linsa on 2018/3/19:16:08.
 * des:
 */

public class ProgressResponseBody extends ResponseBody {


    private ProgressListener progressListener;
    private ResponseBody responseBody;

    //ResponseBody中自带的文件缓存
    private BufferedSource bufferedSource;


    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                progressListener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                return bytesRead;
            }
        };
    }
}
