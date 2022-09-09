package com.jiajia.fluttermodule;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

public class FlutterPageActivity extends FlutterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public FlutterEngine provideFlutterEngine(@NonNull Context context) {
        return FlutterModuleApplication.getInstance().flutterEngine;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        FlutterModuleApplication.getInstance().flutterEngine.destroy();
    }
}