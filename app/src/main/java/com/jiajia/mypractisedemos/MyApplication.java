package com.jiajia.mypractisedemos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.mobile.framework.quinoxless.IInitCallback;
import com.alipay.mobile.framework.quinoxless.QuinoxlessApplication;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;
import com.alipay.mobile.nebula.provider.H5NebulaFileProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappproxy.inside.provider.InsidePresetProviderImpl;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jiajia.basemodule.BuildConfig;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;
import com.jiajia.mypractisedemos.module.webview.H5NebulaFileProviderImpl;
import com.jiajia.mypractisedemos.module.webview.H5RsaProviderImpl;
import com.jiajia.mypractisedemos.module.webview.MyJSApiPlugin;
import com.mpaas.nebula.adapter.api.MPNebula;

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
        // 是否需要考虑进程问题呢？？？
        QuinoxlessFramework.init();
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

        QuinoxlessFramework.setup(this, () -> {
            // 在这里开始使用 mPaaS 功能
            // 初始化小程序公共资源包
            H5Utils.setProvider(H5NebulaFileProvider.class.getName(), new H5NebulaFileProviderImpl());
            MPNebula.registerH5Plugin(MyJSApiPlugin.class.getName(), "", "page",
                    new String[]{MyJSApiPlugin.TINY_TO_NATIVE});
        });
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
