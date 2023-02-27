package com.jiajia.mypractisedemos.module.orientation;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;

import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;

/**
 * Created by Numen_fan on 2023/2/11
 * Desc: 监听系统设置中是否打开自动选择，部分手机厂商叫方向锁定
 */
public class ScreenRotationSettingObserver extends ContentObserver {

    private static final String TAG = "RotationObserver";
    final ContentResolver mResolver;

    private ScreenRotationSettingListener listener;

    public ScreenRotationSettingObserver(Handler handler, ContentResolver resolver) {
        super(handler);
        mResolver = resolver;
    }

    public void setSystemOrientationSettingListener(ScreenRotationSettingListener l) {
        this.listener = l;
    }

    /**
     *  屏幕旋转设置改变时调用
     */
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        if (listener != null) {
            listener.onRotationSettingChanged();
        }
    }

    public void startObserver() {
        if (mResolver == null) {
            LogUtils.INSTANCE.warn(TAG, "mResolver is null");
            return;
        }
        mResolver.registerContentObserver(Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION),
                false, this);
    }

    public void stopObserver() {
        if (mResolver == null) {
            LogUtils.INSTANCE.warn(TAG, "mResolver is null");
            return;
        }
        mResolver.unregisterContentObserver(this);
    }

    /**
     *  监听回调
     */
    public interface ScreenRotationSettingListener {
        void onRotationSettingChanged();
    }


}
