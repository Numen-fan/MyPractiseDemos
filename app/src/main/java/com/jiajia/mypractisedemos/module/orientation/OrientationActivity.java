package com.jiajia.mypractisedemos.module.orientation;


import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;


import androidx.annotation.NonNull;

import com.jiajia.mypractisedemos.BaseActivity;
import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;


public class OrientationActivity extends BaseActivity implements ScreenRotationSettingObserver.ScreenRotationSettingListener,
        ScreenOrientationDetector.OrientationChangeListener {

    private static final String TAG = "OrientationActivity";

    private static final int CHANGE_ORIENTATION = 10086;

    private ScreenRotationSettingObserver mScreenRotationSettingObserver;
    private ScreenOrientationDetector mScreenOrientationDetector;
    private Handler mHandler;

    @Override
    public int getContentResId() {
        return R.layout.activity_orientation;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initParam() {
        mScreenRotationSettingObserver = new ScreenRotationSettingObserver(mHandler, getContentResolver());
        mScreenOrientationDetector = new ScreenOrientationDetector(this, SensorManager.SENSOR_DELAY_NORMAL);
        mScreenRotationSettingObserver.setSystemOrientationSettingListener(this);
        mScreenOrientationDetector.setOrientationChangeListener(this);
        // 初始化Handler
        mHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == CHANGE_ORIENTATION) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    mScreenOrientationDetector.disable(); // 恢复设置后，结束检测
                }
            }
        };
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_change_orientation).setOnClickListener(v -> changeOrientation());
        mScreenRotationSettingObserver.startObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScreenRotationSettingObserver.setSystemOrientationSettingListener(null);
        mScreenOrientationDetector.setOrientationChangeListener(null);
        mScreenOrientationDetector.disable();
        mScreenRotationSettingObserver.stopObserver();
        mHandler.removeMessages(CHANGE_ORIENTATION);
        mHandler = null;
    }

    /**
     * 屏幕自动旋转开关发生变化
     */
    @Override
    public void onRotationSettingChanged() {
        LogUtils.INSTANCE.warn(TAG, "系统自动选择设置发生变化");
        startScreenOrientationListen();
    }

    /**
     * 实时计算的横竖屏发生了变化
     *
     * @param orientation 当前横屏还是竖屏
     */
    @Override
    public void onOrientationChanged(int orientation) {
        LogUtils.INSTANCE.warn(TAG, "横竖屏计算发生变化，当前状态 = " + orientation);
        if (canActivityAutoRotate() && getResources().getConfiguration().orientation == orientation) {
            mHandler.sendEmptyMessageDelayed(CHANGE_ORIENTATION, 500);
        }
    }

    /**
     * 手动改变横竖屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private void changeOrientation() {
        mHandler.removeMessages(CHANGE_ORIENTATION); // 手动切换，移除之前的延迟任务，避免快速点击带来的问题。
        if (isLandscape()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        startScreenOrientationListen();
    }

    /**
     * 打开屏幕旋转监听
     */
    private void startScreenOrientationListen() {
        // 如果系统自动旋转打开，则开启横竖屏切换检测
        if (canActivityAutoRotate()) {
            LogUtils.INSTANCE.warn(TAG, "开启屏幕旋转检测");
            mHandler.postDelayed(() -> {
                mScreenOrientationDetector.initOrientation();
                mScreenOrientationDetector.enable();
            }, 500);
        } else {
            LogUtils.INSTANCE.warn(TAG, "关闭了自动旋转");
            mScreenOrientationDetector.disable(); // 如果关闭了自动旋转，取消一次横竖屏监听
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


    private boolean canActivityAutoRotate() {
        try {
            int value = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
            return value == 1;
        } catch (Settings.SettingNotFoundException e) {
            LogUtils.INSTANCE.error(TAG, "canActivityAutoRotate error", e);
        }
        return false;
    }
}