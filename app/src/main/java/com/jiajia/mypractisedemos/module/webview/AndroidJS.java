package com.jiajia.mypractisedemos.module.webview;

import android.webkit.JavascriptInterface;

import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;

/**
 * Created by Numen_fan on 2023/5/26
 * Desc:
 */
public class AndroidJS {

    @JavascriptInterface
    public void hello(String str) {
        ToastUtils.INSTANCE.showToast(str);
    }

}
