package com.jiajia.mypractisedemos.module.webview;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiajia.mypractisedemos.BaseActivity;
import com.jiajia.mypractisedemos.R;

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    public int getContentResId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initUI() {
        mWebView = findViewById(R.id.my_webview);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initParam() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 注入交互对象
        mWebView.addJavascriptInterface(new AndroidJS(), "androidJS");

        mWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void initListener() {

        mWebView.setWebViewClient(new WebViewClient() {
            // webview内打开链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.clearHistory();

        ((ViewGroup) mWebView.getParent()).removeView(mWebView);
        mWebView.destroy();
        mWebView = null;
    }
}