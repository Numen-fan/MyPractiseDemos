package com.jiajia.mypractisedemos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by fanjiajia02 on 2022/3/10
 * Desc: Activity基类
 **/
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResId());

        initUI();

        initListener();
    }

    public abstract int getContentResId();

    public abstract void initUI();

    public abstract void initListener();

    /**
     * 启动该Activity
     * @param context 建议Activity
     */
    public static void startActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
