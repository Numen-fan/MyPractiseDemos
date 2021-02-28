package com.jiajia.mypractisedemos;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context context;

    private static MyApplication instance = null;

    public MyApplication() {
        super();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();

        context = this;

    }
}
