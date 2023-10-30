package com.jiajia.mypractisedemos.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.jiajia.mypractisedemos.MyApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    private static final String TAG = "Utils";

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

    public static int getScreenWidthNew(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            return dm.widthPixels;
        }
        return 0;
    }

    public static int getScreenHeightNew(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            return dm.heightPixels;
        }
        return 0;
    }

    /**
     * 获得状态栏的高度
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

    public static void openUrlWithSystemBrowser(final Context context, final String url) {
        try {
            final Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception exception) {
            Log.e(TAG, "openUrlWithSystemBrowser error.", exception);
            Toast.makeText(context, "打开失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setCustomDensity(Activity activity) {
        final DisplayMetrics displayMetrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        final float targetDensity = displayMetrics.widthPixels / 375f;
        final int targetDensityDpi = (int) (160 * targetDensity);

//        displayMetrics.density = displayMetrics.scaledDensity = targetDensity;
//        displayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = activityDisplayMetrics.scaledDensity = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    private static boolean findHookAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager
                .getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : applicationInfoList) {
            if (applicationInfo.packageName.equals("de.robv.android.xposed.installer")) {
                Log.wtf("HookDetection", "Xposed found on the system.");
                return true;
            }
            if (applicationInfo.packageName.equals("com.saurik.substrate")) {
                Log.wtf("HookDetection", "Substrate found on the system.");
                return true;
            }
        }
        return false;
    }

    private static boolean findHookAppFile() {
        try {
            Set<String> libraries = new HashSet<String>();
            String mapsFilename = "/proc/" + android.os.Process.myPid() + "/maps";
            BufferedReader reader = new BufferedReader(new FileReader(mapsFilename));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(".so") || line.endsWith(".jar")) {
                    int n = line.lastIndexOf(" ");
                    libraries.add(line.substring(n + 1));
                }
            }
            reader.close();
            for (String library : libraries) {
                if (library.contains("com.saurik.substrate")) {
                    Log.wtf("HookDetection", "Substrate shared object found: " + library);
                    return true;
                }
                if (library.contains("XposedBridge.jar")) {
                    Log.wtf("HookDetection", "Xposed JAR found: " + library);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.wtf("HookDetection", e.toString());
        }
        return false;
    }

    private static boolean findHookStack() {
        try {
            throw new Exception("findhook");
        } catch (Exception e) {

            // 读取栈信息
            // for(StackTraceElement stackTraceElement : e.getStackTrace()) {
            // Log.wtf("HookDetection", stackTraceElement.getClassName() + "->"+
            // stackTraceElement.getMethodName());
            // }

            int zygoteInitCallCount = 0;
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    zygoteInitCallCount++;
                    if (zygoteInitCallCount == 2) {
                        Log.wtf("HookDetection", "Substrate is active on the device.");
                        return true;
                    }
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2")
                        && stackTraceElement.getMethodName().equals("invoked")) {
                    Log.wtf("HookDetection", "A method on the stack trace has been hooked using Substrate.");
                    return true;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge")
                        && stackTraceElement.getMethodName().equals("main")) {
                    Log.wtf("HookDetection", "Xposed is active on the device.");
                    return true;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge")
                        && stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    Log.wtf("HookDetection", "A method on the stack trace has been hooked using Xposed.");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isHook(Context context) {
        if (findHookAppName(context) || findHookAppFile() || findHookStack()) {
            return true;
        }
        return false;
    }
}
