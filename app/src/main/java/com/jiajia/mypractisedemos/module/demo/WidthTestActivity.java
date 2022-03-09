package com.jiajia.mypractisedemos.module.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jiajia.mypractisedemos.MyApplication;
import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.utils.Utils;

public class WidthTestActivity extends AppCompatActivity {

    private static final String TAG = "WidthTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_width_test);

        View btnGetWidth = findViewById(R.id.btn_get_width);

        btnGetWidth.setOnClickListener(v -> {

            Log.d(TAG, "Activity get width " + Utils.getScreenWidth(this));

            Log.d(TAG, "Application get width " + Utils.getScreenWidth(MyApplication.context));

            Log.d(TAG, "Activity get width new " + Utils.getScreenWidthNew(this));

            Log.d(TAG, "Application get width new " + Utils.getScreenWidthNew(MyApplication.context));

            Log.d(TAG, "Get real width " + Utils.getFullScreenWidth());

        });

    }
}