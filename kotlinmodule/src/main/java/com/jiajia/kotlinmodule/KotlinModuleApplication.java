package com.jiajia.kotlinmodule;

import android.app.Application;
import android.util.Log;

/**
 * Created by fanjiajia02 on 2022/8/26
 * Desc:
 **/
public class KotlinModuleApplication extends Application {

    private static final String TAG = "KotlinModuleApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "onCreate");
    }
}
