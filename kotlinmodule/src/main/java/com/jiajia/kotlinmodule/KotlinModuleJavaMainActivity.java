package com.jiajia.kotlinmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jiajia.basemodule.config.RouteConfig;

@Route(path = RouteConfig.KOTLIN_JAVA_MAIN_ACTIVITY)
public class KotlinModuleJavaMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kotlin_module_activity_java_main);

        findViewById(R.id.tv_tips).setOnClickListener(v -> {
            ARouter.getInstance().build("/app/DemoActivity").navigation();
        });
    }
}