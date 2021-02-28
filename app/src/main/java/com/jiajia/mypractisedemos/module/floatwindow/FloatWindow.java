package com.jiajia.mypractisedemos.module.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import androidx.core.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.jiajia.mypractisedemos.MyApplication;
import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.utils.Utils;

public class FloatWindow {
    private static final String TAG = "FloatWindow";

    private static volatile FloatWindow instance;
    private Context mContext;

    // 窗体管理
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private int type;

    private static final int PADDING = Utils.dp2px(8);
    private final int X_MIN;
    private final int Y_MIN;

    // view相关
    View floatView;

    public FloatWindow() {
        mContext = MyApplication.context;
        X_MIN = Y_MIN = PADDING;
        initView();
    }

    public static FloatWindow getInstance() {
        if (instance == null) {
            synchronized (FloatWindow.class) {
                if (instance == null) {
                    instance = new FloatWindow();
                }
            }
        }
        return instance;
    }

    private void initView() {
        windowManager = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        type = WindowManager.LayoutParams.TYPE_TOAST;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1){
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        layoutParams = new WindowManager.LayoutParams(Utils.dp2px(100), Utils.dp2px(100), type,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.x = Utils.getScreenWidth(MyApplication.context) - layoutParams.width - PADDING;
        layoutParams.y = Utils.dp2px(51);

        floatView = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.float_windows, null, false);

        floatView.setOnTouchListener(new FloatWindTouch());
    }

    public void show() {
        if (windowManager != null) {
            try {
                windowManager.addView(floatView, layoutParams);
            } catch (Exception e) {
                Log.e(TAG, "show: " + e.getMessage());
            }
        }
    }

    class FloatWindTouch implements View.OnTouchListener {

        private int x;
        private int y;
        private int downX;
        private int downY;


        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // getRowX是相对屏幕的位置，getX是相对View的位置
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    downX = x;
                    downY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int distanceX = nowX - x;
                    int distanceY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + distanceX;
                    layoutParams.y = layoutParams.y + distanceY;
                    if (layoutParams.x + layoutParams.width > Utils.getScreenWidth(mContext)) {
                        layoutParams.x = Utils.getScreenWidth(mContext) - layoutParams.width;
                    }
                    if (layoutParams.x < X_MIN) {
                        layoutParams.x = X_MIN;
                    }
                    if (layoutParams.y + layoutParams.height > Utils.getScreenHeight(mContext)) {
                        layoutParams.y = Utils.getScreenHeight(mContext) - layoutParams.height;
                    }
                    if (layoutParams.y < Y_MIN) {
                        layoutParams.y = Y_MIN;
                    }

//                    Log.d(TAG, "onTouch: getRowX=" + event.getRawY() + ", getX="
//                            + event.getX() + ",view.getMeasuredWidth=" + view.getMeasuredWidth());
                    updateViewLayout(view);
                    break;
                case MotionEvent.ACTION_UP:

                    break;
                default:
                    break;
            }
            return false;
        }

        void updateViewLayout(View view) {
            if (ViewCompat.isAttachedToWindow(view)) {
                windowManager.updateViewLayout(view, layoutParams);
            }
        }
    }

}
