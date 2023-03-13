package com.jiajia.mypractisedemos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jiajia.basemodule.BuildConfig;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;

import java.lang.reflect.Method;
import java.util.Stack;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @SuppressLint("StaticFieldLeak")
    private static MyApplication instance = null;

    protected static final Stack<Activity> activities = new Stack<>();

    private Application kotlinModuleApplication;


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

        Fresco.initialize(this);

        if (kotlinModuleApplication != null) {
            kotlinModuleApplication.onCreate();
        }

        // ARouter 配置
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        kotlinModuleApplication = getKotlinModuleApplication(this);

        try {
            @SuppressLint("DiscouragedPrivateApi")
            Method method = Application.class.getDeclaredMethod("attach", Context.class);
            method.setAccessible(true);
            method.invoke(kotlinModuleApplication, getBaseContext());
        } catch (Exception e) {
            LogUtils.INSTANCE.error(TAG, "attachBaseContext error", e);
        }
    }

    private Application getKotlinModuleApplication(Context context) {
        try {
            if (kotlinModuleApplication == null) {
                ClassLoader classLoader = context.getClassLoader();
                if (classLoader != null) {
                    Class<?> clazz = classLoader.loadClass("com.jiajia.kotlinmodule.KotlinModuleApplication");
                    if (clazz != null) {
                        kotlinModuleApplication = (Application) clazz.newInstance();
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.INSTANCE.warn(TAG, "getKotlinModuleApplication error");
        }

        return kotlinModuleApplication;
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
