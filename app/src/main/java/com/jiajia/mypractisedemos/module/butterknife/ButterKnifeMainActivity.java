package com.jiajia.mypractisedemos.module.butterknife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.TextView;

import com.jiajia.butterknife.ButterKnife;
import com.jiajia.butterknife.MyUnBinder;
import com.jiajia.butterknifeannotationlib.MyBindView;
import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;

import java.util.Objects;


public class ButterKnifeMainActivity extends AppCompatActivity {

    @MyBindView(R.id.tv_text1)
    TextView tvText1;

    private MyUnBinder mUnBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
//        getWindow().getDecorView().setFitsSystemWindows(false);
        // 2. 设置 System bar 透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_butter_knife_main);
        mUnBinder = ButterKnife.bind(this);
        tvText1.setText("我已经不是空的了");

        tvText1.setOnClickListener(v-> {
            int top = Objects.requireNonNull(ViewCompat.getRootWindowInsets(getWindow().getDecorView()))
                    .getInsets(WindowInsetsCompat.Type.navigationBars()).top;
            int bottom = Objects.requireNonNull(ViewCompat.getRootWindowInsets(getWindow().getDecorView()))
                    .getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
            LogUtils.INSTANCE.warn("ButterKnifeMainActivity", "ButterKnifeMainActivity:: top = " + top + ", bottom = " + bottom);
            LogUtils.INSTANCE.warn("ButterKnifeMainActivity", "getNavigationBarHeight = " + getNavigationBarHeight(this));

            if ((WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS & getWindow().getAttributes().flags)
                    == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) {
                LogUtils.INSTANCE.warn("ButterKnifeMainActivity", "沉浸了啊 " + getNavigationBarHeight(this));
            } else {
                LogUtils.INSTANCE.warn("ButterKnifeMainActivity", "没有沉浸了啊 = " + getNavigationBarHeight(this));
            }
        });

        new Thread(() -> {
            Looper.prepare();
            Handler handler = new Handler();
        }).start();

    }

    //获取导航栏高度
    public static int getNavigationBarHeight(@NonNull Context context) {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }

        return result ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }
}