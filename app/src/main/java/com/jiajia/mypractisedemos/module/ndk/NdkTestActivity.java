package com.jiajia.mypractisedemos.module.ndk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;

public class NdkTestActivity extends AppCompatActivity {

    private static final String TAG = "NdkTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_test);
        String text = NDKTools.getStringFromNDK();
        ((TextView)findViewById(R.id.tv_ndk_string)).setText(text);

    }
}