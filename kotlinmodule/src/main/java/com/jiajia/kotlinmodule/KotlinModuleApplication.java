package com.jiajia.kotlinmodule;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Created by fanjiajia02 on 2022/8/26
 * Desc:
 **/
public class KotlinModuleApplication extends Application {

    private static final String TAG = "KotlinModuleApplication";

    private static KotlinModuleApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "onCreate");

        instance = this;

    }

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w(TAG, "onConfigurationChanged");
    }
}
