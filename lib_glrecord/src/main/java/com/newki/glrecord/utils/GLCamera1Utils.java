package com.newki.glrecord.utils;

import android.app.Application;

/**
 * Created by Numen_fan on 2024/5/23
 * Desc:
 */
public class GLCamera1Utils {

    public static Application application;

    public static void init(Application context) {
        application = context;
    }

    public static Application getApplicationContext() {
        if (application == null) {
            throw new RuntimeException("application is null!");
        }
        return application;
    }

}
