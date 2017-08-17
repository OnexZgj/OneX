package com.example.linsa.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.weidget.Html5WebView;

import lib.homhomlib.design.SlidingLayout;

public class Html5Activity extends AppCompatActivity {

    private SlidingLayout mLayout;
    private Html5WebView mWebView;
    private String mUrl="https://mp.weixin.qq.com/s/ZUXB2BdRYh0AGv9Ba41ZHQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html5);

        mLayout = (SlidingLayout) findViewById(R.id.web_layout);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        mWebView = new Html5WebView(this);
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);
        mWebView.loadUrl(mUrl);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId){
            case R.id.close:
                Html5Activity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }



    private long mOldTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mOldTime < 1500) {
                mWebView.clearHistory();
                mWebView.loadUrl(mUrl);
            } else if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                Html5Activity.this.finish();
            }
            mOldTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }


}
