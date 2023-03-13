package com.jiajia.mypractisedemos.module.orientation;

import android.content.Context;
import android.view.OrientationEventListener;

/**
 * Created by Numen_fan on 2023/2/11
 * Desc: 时刻检测屏幕的旋转角度，并计算当前的横竖屏状态
 */
public class ScreenOrientationDetector extends OrientationEventListener {
    private int mCurrentOrientation;

    public static final int ORIENTATION_UNDEFINED = 0;

    public static final int ORIENTATION_PORTRAIT = 1;

    public static final int ORIENTATION_LANDSCAPE = 2;

    private int currentOrientation = ORIENTATION_UNDEFINED;

    private OrientationChangeListener listener;

    public ScreenOrientationDetector(Context context, int rate) {
        super(context, rate);
    }

    public void setOrientationChangeListener(OrientationChangeListener l) {
        this.listener = l;
    }

    private int getOrientation() {
        if (this.mCurrentOrientation != 0 && this.mCurrentOrientation != 180) {
            return this.mCurrentOrientation != 90 && this.mCurrentOrientation != 270
                    ? ORIENTATION_UNDEFINED : ORIENTATION_LANDSCAPE;
        } else {
            return ORIENTATION_PORTRAIT;
        }
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (orientation != ORIENTATION_UNKNOWN) {
            if (orientation >= 45 && orientation <= 315) {
                if (orientation > 45 && orientation < 135) {
                    this.mCurrentOrientation = 90;
                } else if (orientation > 135 && orientation < 225) {
                    this.mCurrentOrientation = 180;
                } else if (orientation > 225 && orientation < 315) {
                    this.mCurrentOrientation = 270;
                }
            } else {
                this.mCurrentOrientation = 0;
            }

            int newOrientation = getOrientation();
            if (ORIENTATION_UNDEFINED != newOrientation && newOrientation != currentOrientation) {
                currentOrientation = newOrientation;
                if (listener != null) {
                    listener.onOrientationChanged(currentOrientation);
                }
            }
        }
    }

    public void initOrientation() {
        currentOrientation = ORIENTATION_UNDEFINED;
    }

    /**
     * 计算出屏幕发生旋转，就会触发
     */
    public interface OrientationChangeListener {
        void onOrientationChanged(int orientation);
    }
}

