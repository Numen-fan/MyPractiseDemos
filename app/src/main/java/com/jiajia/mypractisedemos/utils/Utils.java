package com.jiajia.mypractisedemos.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import com.jiajia.mypractisedemos.MyApplication;

public class Utils {

    public static int dp2px(final float dp) {
        final float scale = MyApplication.context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm == null ? 0 : wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm == null ? 0 : wm.getDefaultDisplay().getHeight();
    }

    public static int getFullScreenWidth() {
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getRealSize(point);
            return point.x;
        } else {
            return 0;
        }
    }

    public static int getFullScreenHeight() {
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getRealSize(point);
            return point.y;
        } else {
            return 0;
        }
    }

    /**
     * 获得状态栏的高度
     * @param context
     * @return
     * by Hankkin at:2015-10-07 21:16:43
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
