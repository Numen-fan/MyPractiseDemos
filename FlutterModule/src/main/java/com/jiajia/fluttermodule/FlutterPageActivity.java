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

//        getIntent().putExtra("route", "FormTestPage"); // 这句话就可以指定跳到对应的路由页面

        super.onCreate(savedInstanceState);

    }
}