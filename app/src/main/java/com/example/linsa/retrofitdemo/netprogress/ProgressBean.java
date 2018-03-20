package com.example.linsa.retrofitdemo.netprogress;

/**
 * Created by Linsa on 2018/3/19:16:34.
 * des:
 */

public class ProgressBean {
    private long bytesRead;
    private long ContentLength;
    private boolean done;

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getContentLength() {
        return ContentLength;
    }

    public void setContentLength(long contentLength) {
        ContentLength = contentLength;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
