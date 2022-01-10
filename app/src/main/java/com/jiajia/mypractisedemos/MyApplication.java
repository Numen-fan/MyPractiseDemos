package com.jiajia.mypractisedemos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jiajia.mypractisedemos.module.flutter.FlutterTools;

import java.util.Stack;

public class MyApplication extends Application {

    public static Context context;

    @SuppressLint("StaticFieldLeak")
    private static MyApplication instance = null;

    protected static final Stack<Activity> activities = new Stack<>();

    public MyApplication() {
        super();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        registerActivityLifecycle();

        // flutter引擎初始化
        FlutterTools.INSTANCE.initFlutterEngine(this);
    }

    public Activity getTopActivity() {
        if (activities.empty()) {
            return null;
        }
        return activities.peek();
    }

    /**
     * 注册activity的生命周期监听，维护{@link #activities}
     */
    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity,
                                          @Nullable Bundle savedInstanceState) {
                activities.push(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity,
                                                    @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                activities.pop();
            }
        });
    }
}
