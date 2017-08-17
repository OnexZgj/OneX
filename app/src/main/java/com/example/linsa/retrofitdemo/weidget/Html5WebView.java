package com.example.linsa.retrofitdemo.weidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.linsa.retrofitdemo.R;
import com.example.linsa.retrofitdemo.util.NetStateUtils;

/**
 * Created by Linsa on 2017/8/16:10:04.
 * des:自定义的WebView的使用
 */

public class Html5WebView extends WebView {
    private Context mContext;
    private ProgressBar mProgressBar;
    private WebsiteChangeListener mWebsiteChangeListener;

    public Html5WebView(Context context) {
        this(context, null);
    }

    public Html5WebView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    /**
     * 初始化WebView的相关属性
     */
    private void init() {

        //添加一个进度条
        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8);
        mProgressBar.setLayoutParams(layoutParams);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.web_progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);


        WebSettings mWebSettings = getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);
        saveData(mWebSettings);
        setWebChromeClient(webChromeClient);
        newWin(mWebSettings);
        setWebViewClient(webViewClient);

    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }


    WebViewClient webViewClient = new WebViewClient() {
        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.d("Url:", url);
            if (mWebsiteChangeListener!=null) {
                mWebsiteChangeListener.onUrlChange(url);
            }
            return true;
        }
    };

    /**
     * 设置数据缓存
     *
     * @param mWebSettings
     */
    private void saveData(WebSettings mWebSettings) {
        //根据网络状态是否进行加载缓存
        if (NetStateUtils.isConnected(mContext)) {
            //有网络，直接在进行网络数据加载
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //没有网，数据从本地的缓存中进行加载
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String appCachePath = mContext.getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);


    }

    WebChromeClient webChromeClient = new WebChromeClient() {


        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================


        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            HitTestResult result = view.getHitTestResult();
            String data = result.getExtra();
            view.loadUrl(data);
            return true;
        }


        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress==100){
                mProgressBar.setVisibility(GONE);
            }else{
                if (mProgressBar.getVisibility()==GONE)
                mProgressBar.setVisibility(VISIBLE);
            }
            mProgressBar.setProgress(newProgress);


            super.onProgressChanged(view, newProgress);
        }

    };

    public interface WebsiteChangeListener{
        void onWebsiteChange(String title);
        void onUrlChange(String url);
//        void onWebsiteChangeBackTop();
    }

    public void setWebsiteChangeListener(WebsiteChangeListener websiteChangeListener){
        this.mWebsiteChangeListener = websiteChangeListener;

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);

    }


}
