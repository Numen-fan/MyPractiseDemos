package com.jiajia.fluttermodule;

import android.app.Application;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineGroup;

/**
 * Created by fanjiajia02 on 2022/8/26
 * Desc:
 **/
public class FlutterModuleApplication {

    private static Application context;

    private static FlutterModuleApplication instance;

    FlutterEngineGroup flutterEngineGroup;

    FlutterEngine flutterEngine;


    public FlutterModuleApplication(Application application) {
        context = application;
        instance = this;
    }


    public void onCreate() {
        flutterEngineGroup = new FlutterEngineGroup(context);
        flutterEngine = flutterEngineGroup.createAndRunDefaultEngine(context);
    }

    public static FlutterModuleApplication  getInstance() {
        return instance;
    }
}
