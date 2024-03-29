package com.jiajia.mypractisedemos.module.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jiajia.mypractisedemos.MyApplication;
import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;
import com.jiajia.mypractisedemos.utils.Utils;

/**
 * Created by sharezer on 2017/5/20.
 */

public class FloatView extends LinearLayout implements DisplayManager.DisplayListener {

    private static final String TAG = "FloatView";

    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private Context mContext;
    private int screenWidth;
    boolean isShow = false;
    private View.OnClickListener mClickListener;

    private WindowManager windowManager;

    private final WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams();

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatView(Context context) {
        super(context);
        mContext = context;
        initView(context);

        DisplayManager mDisplayManager = (DisplayManager) MyApplication.getInstance().getSystemService(Context.DISPLAY_SERVICE);
        mDisplayManager.registerDisplayListener(this, new Handler(Looper.getMainLooper()));
    }

    // 初始化窗体
    public void initView(Context context) {
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = Utils.getScreenWidth(context);
        LayoutInflater.from(context).inflate(R.layout.float_windows, this);
        windowManagerParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            windowManagerParams.type =WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }

        windowManagerParams.format = PixelFormat.RGBA_8888; // 背景透明
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 调整悬浮窗口至左上角，便于调整坐标
        windowManagerParams.gravity = Gravity.START | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值
        windowManagerParams.x = screenWidth;
        windowManagerParams.y = 0;
        // 设置悬浮窗口长宽数据
        windowManagerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        this.setLayoutParams(windowManagerParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获取到状态栏的高度
        int statusBarHeight = Utils.getStatusHeight(mContext);
        // 获取相对屏幕的坐标，悬浮窗口所在的位置
        x = event.getRawX();
        y = event.getRawY() - statusBarHeight;

        int startX = 0;
        int startY = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mTouchX = event.getX();
                mTouchY = event.getY();
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;

            }
            case MotionEvent.ACTION_MOVE: {
                updateViewPosition();
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (Math.abs(x - startX) < 8 && Math.abs(y - startY + statusBarHeight) < 8) {
                    if (mClickListener != null) {
                        mClickListener.onClick(this);
                    }
                }
                if(inRangeOfView(this, event))
                {
                    if(mClickListener != null) mClickListener.onClick(this);
                }

                if (x <= screenWidth >> 1) {
                    x = 0;
                } else {
                    x = screenWidth;
                }
                updateViewPosition();

                break;
            }
        }

        return super.onTouchEvent(event);
    }

    private boolean inRangeOfView(View view, MotionEvent ev){
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        if(ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())){
            return false;
        }
        return true;
    }

    // 隐藏该窗体
    public void hide() {
        if (isShow) {
            windowManager.removeView(this);
            isShow = false;
        }

    }

    // 显示该窗体
    public void show() {
        if (!isShow) {
            windowManager.addView(this, windowManagerParams);
            isShow = true;
        }

    }

    public boolean isShow() {return isShow;}

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    private void updateViewPosition() {
        // 更新浮动窗口位置参数
        windowManagerParams.x = (int) (x - mTouchX);
        windowManagerParams.y = (int) (y - mTouchY);
        windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
    }


    @Override
    public void onDisplayAdded(int displayId) {

    }

    @Override
    public void onDisplayRemoved(int displayId) {

    }

    @Override
    public void onDisplayChanged(int displayId) {
        LogUtils.INSTANCE.warn(TAG, "height = " + Utils.getFullScreenHeight() + ", width = " + Utils.getFullScreenWidth());

    }
}