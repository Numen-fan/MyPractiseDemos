package com.jiajia.mypractisedemos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.quinoxless.QuinoxlessPrivacyUtil;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.jiajia.basemodule.config.RouteConfig;
import com.jiajia.mypractisedemos.module.TipsActivity;
import com.jiajia.mypractisedemos.module.aidl.AIDLActivity;
import com.jiajia.mypractisedemos.module.audio.AudioActivity;
import com.jiajia.mypractisedemos.module.citychange.CityChangeActivity;
import com.jiajia.mypractisedemos.module.compose.ComposeMainActivity;
import com.jiajia.mypractisedemos.module.decoration.DecorationActivity;
import com.jiajia.mypractisedemos.module.demo.DemoActivity;
import com.jiajia.mypractisedemos.module.demo.MotionLayoutActivity;
import com.jiajia.mypractisedemos.module.dialog.DialogActivity;
import com.jiajia.mypractisedemos.module.edittextview.EditTextActivity;
import com.jiajia.mypractisedemos.module.expendablelayout.ExpendableLayoutActivity;
import com.jiajia.mypractisedemos.module.floatwindow.FloatView;
import com.jiajia.mypractisedemos.module.jetpack.JetpackActivity;
import com.jiajia.mypractisedemos.module.kotlin.activity.KotlinActivity;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;
import com.jiajia.mypractisedemos.module.manfunctionsui.ManyFunctionUIActivity;
import com.jiajia.mypractisedemos.module.mvpdemo.view.LoginMvpActivity;
import com.jiajia.mypractisedemos.module.mylinearlayout.MyLinearLayoutActivity;
import com.jiajia.mypractisedemos.module.navigation.NavigationActivity;
import com.jiajia.mypractisedemos.module.ndk.NdkTestActivity;
import com.jiajia.mypractisedemos.module.orientation.OrientationActivity;
import com.jiajia.mypractisedemos.module.picturescale.PictureScaleActivity;
import com.jiajia.mypractisedemos.module.popwindow.PopwindowActivity;
import com.jiajia.mypractisedemos.module.recycgroup1.RecycGroup1Activity;
import com.jiajia.mypractisedemos.module.seekbar.SeekBarActivity;
import com.jiajia.mypractisedemos.module.trainrecyclerview.Trainrecyclerview;
import com.jiajia.mypractisedemos.module.webview.WebViewActivity;
import com.jiajia.mypractisedemos.module.wheeldialog.WheelActivity;
import com.jiajia.mypractisedemos.module.widgetdemo.WidgetDemoActivity;
import com.jiajia.mypractisedemos.utils.PermissionUtil;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.ta.utdid2.android.utils.SystemProperties;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnItemClickListener<String> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final List<String> funcNames = new ArrayList<>();

    private static final String FLOAT_GROUP = "floatGroup";
    private static final String DECORATION = "decoration";
    private static final String START = "start";
    private static final String TRAIN = "train";
    private static final String CITY_CHANGE = "cityChange";
    private static final String PICTURE_SCALE = "picScale";
    private static final String MVP = "mvp";
    private static final String EXPANDABLE = "expandable";
    private static final String MANY_FUNC = "manyFunc";
    private static final String POPUP_WIND = "popupwind";
    private static final String DIALOG = "dialog";
    private static final String JETPACK = "jetpack";
    private static final String LINEARLAYOUT = "linearlayout";
    private static final String SEEKBAR = "seekbar";
    private static final String DEMO = "Demo";
    private static final String AUDIO = "Audio";
    private static final String FLOAT_WIND = "FloatWind";
    private static final String LIVE_REPLAY = "LiveReplay";
    private static final String WIDGET = "Widget";
    private static final String KOTLIN = "Kotlin";
    private static final String EDITTEXT = "EditText";
    private static final String COMPOSE = "Compose";
    private static final String TIPS = "Tips";
    private static final String MOTION = "Motion";
    private static final String FLUTTER = "flutter";
    private static final String NDK = "NDK";
    private static final String AROUTER = "ARouter";
    private static final String ORIENTATION = "Orientation";
    private static final String NAVIGATION = "navigation";
    private static final String HOOK_AT = "Hook";
    private static final String AIDL = "AIDL";
    private static final String WEBVIEW = "webview";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView funcList = findViewById(R.id.func_list);
        FuncAdapter adapter = new FuncAdapter();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        funcList.setAdapter(adapter);
        funcList.setLayoutManager(layoutManager);
        adapter.setClickListener(this);
        getFuncNames();
        adapter.setData(funcNames);

//        QuinoxlessPrivacyUtil.sendPrivacyAgreedBroadcast(getApplicationContext());

    }


    public void getFuncNames() {
        funcNames.add(FLOAT_GROUP);
        funcNames.add(DECORATION);
        funcNames.add(START);
        funcNames.add(TRAIN);
        funcNames.add(CITY_CHANGE);
        funcNames.add(PICTURE_SCALE);
        funcNames.add(MVP);
        funcNames.add(MANY_FUNC);
        funcNames.add(EXPANDABLE);
        funcNames.add(POPUP_WIND);
        funcNames.add(DIALOG);
        funcNames.add(JETPACK);
        funcNames.add(LINEARLAYOUT);
        funcNames.add(SEEKBAR);
        funcNames.add(DEMO);
        funcNames.add(AUDIO);
        funcNames.add(LIVE_REPLAY);
        funcNames.add(WIDGET);
        funcNames.add(KOTLIN);
        funcNames.add(EDITTEXT);
        funcNames.add(FLOAT_WIND);
        funcNames.add(COMPOSE);
        funcNames.add(TIPS);
        funcNames.add(MOTION);
        funcNames.add(FLUTTER);
        funcNames.add(NDK);
        funcNames.add(AROUTER);
        funcNames.add(ORIENTATION);
        funcNames.add(NAVIGATION);
        funcNames.add(HOOK_AT);
        funcNames.add(AIDL);
        funcNames.add(WEBVIEW);

    }

    @Override
    public void onClick(String item) {
        switch (item) {
            case FLOAT_GROUP:
                BaseActivity.startActivity(this, RecycGroup1Activity.class);
                break;
            case DECORATION:
                BaseActivity.startActivity(this, DecorationActivity.class);
                break;
            case START:
                BaseActivity.startActivity(this, WheelActivity.class);
                break;
            case TRAIN:
                BaseActivity.startActivity(this, Trainrecyclerview.class);
                break;
            case CITY_CHANGE:
                BaseActivity.startActivity(this, CityChangeActivity.class);
                break;
            case PICTURE_SCALE:
                BaseActivity.startActivity(this, PictureScaleActivity.class);
                break;
            case MVP:
                BaseActivity.startActivity(this, LoginMvpActivity.class);
                break;
            case EXPANDABLE:
                BaseActivity.startActivity(this, ExpendableLayoutActivity.class);
                break;
            case MANY_FUNC:
                BaseActivity.startActivity(this, ManyFunctionUIActivity.class);
                break;
            case POPUP_WIND:
                BaseActivity.startActivity(this, PopwindowActivity.class);
                break;
            case DIALOG:
                BaseActivity.startActivity(this, DialogActivity.class);
                break;
            case JETPACK:
                BaseActivity.startActivity(this, JetpackActivity.class);
                break;
            case LINEARLAYOUT:
                BaseActivity.startActivity(this, MyLinearLayoutActivity.class);
                break;
            case DEMO:
                BaseActivity.startActivity(this, DemoActivity.class);
                break;
            case SEEKBAR:
                BaseActivity.startActivity(this, SeekBarActivity.class);
                break;
            case AUDIO:
                BaseActivity.startActivity(this, AudioActivity.class);
                break;
            case FLOAT_WIND:
                handleClickEvent(FLOAT_WIND);
                break;
            case WIDGET:
                BaseActivity.startActivity(this, WidgetDemoActivity.class);
                break;
            case KOTLIN:
                BaseActivity.startActivity(this, KotlinActivity.class);
                break;
            case EDITTEXT:
                BaseActivity.startActivity(this, EditTextActivity.class);
                break;
            case COMPOSE:
                BaseActivity.startActivity(this, ComposeMainActivity.class);
                break;
            case TIPS:
                BaseActivity.startActivity(this, TipsActivity.class);
                break;
            case MOTION:
                BaseActivity.startActivity(this, MotionLayoutActivity.class);
                break;
            case FLUTTER:
                // 这个必须走FlutterEngineCache
//                startActivity(FlutterActivity.withCachedEngine("main").build(this));
//                BaseActivity.startActivity(this, FlutterPageActivity.class);
                break;
            case NDK:
                BaseActivity.startActivity(this, NdkTestActivity.class);
                break;
            case AROUTER:
                ARouter.getInstance().build(RouteConfig.KOTLIN_MAIN_ACTIVITY).navigation();
                break;
            case ORIENTATION:
                BaseActivity.startActivity(this, OrientationActivity.class);
                break;
            case NAVIGATION:
                BaseActivity.startActivity(this, NavigationActivity.class);
                break;
            case HOOK_AT:
                hookStartActivity();
                break;
            case AIDL:
                BaseActivity.startActivity(this, AIDLActivity.class);
                break;
            case WEBVIEW:
                BaseActivity.startActivity(this, WebViewActivity.class);
                break;
            default:
                ToastUtils.INSTANCE.showToast("丫的，没实现方法");
                break;
        }
    }

    private void hookStartActivity() {
        try {

            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
//            Field field = activityThreadClass.getDeclaredField("sCurrentActivityThread");
//            Object activityThread = field.get(null);

//            @SuppressLint("BlockedPrivateApi")
            Method method = activityThreadClass.getMethod("getLaunchingActivity", IBinder.class);

            LogUtils.INSTANCE.warn(TAG, method.getName());

        } catch (Exception e) {
            LogUtils.INSTANCE.error(TAG, "hookStartActivity error", e);
        }


    }

    /**
     * 在主界面处理不需要启动相关Activity的点击事件
     *
     */
    private void handleClickEvent(String func) {
        switch (func) {
            case FLOAT_WIND:
                // 权限测试
//                LogUtils.INSTANCE.warn(TAG, "shouldShowRequestPermissionRationale "
//                        + this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
//                        + ", ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)"
//                        + ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA));
//                if ( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 10086);
//                } else {
//                    LogUtils.INSTANCE.warn(TAG, "无权限");
//                }
                // 悬浮窗
//                launchFloatWindow();
                // webview
//                MPNebula.startApp("2023060520230605");
//                MPNebula.startUrl("http://192.168.232.61:8080/test.html");
//                MPNebula.startUrl("file:///android_asset/javascript2.html");
//
//                getWindow().getDecorView().postDelayed(()-> {
//                    H5Service h5Service = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
//                    h5Service.getTopH5Page().getBridge().sendDataWarpToWeb("h5NetworkChange", null, null);
//                }, 5_000);

                String androidId = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                ToastUtils.INSTANCE.showToast(androidId);

                // SDK_INT
                LogUtils.INSTANCE.warn(TAG, "SDK_INT" + SystemProperties.get("ro.build.version.sdk"));

                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtils.INSTANCE.warn(TAG, "result is b " + grantResults[0]);
    }

    /**
     * 悬浮窗
     */
    private void launchFloatWindow() {
        if (PermissionUtil.canDrawOverlays(this)) {
            FloatView floatView = new FloatView(this); // 创建窗体
            floatView.setOnClickListener(v -> {
            }); // 设置事件，你需要实现FloatView里的onclick接口
            floatView.show(); // 显示该窗体
        } else {
            PermissionUtil.requestOverlayPermission(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionUtil.REQUEST_OVERLAY) {
            if (resultCode == Activity.RESULT_OK) {
                launchFloatWindow();
            } else {
                Toast.makeText(this, "没有权限，无法使用！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Android 12 开始，主页面返回时，不再执行
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.INSTANCE.error(TAG, "onDestroy");
    }

    private static class FuncAdapter extends RecyclerView.Adapter<FuncAdapter.FuncHolder> {

        private final List<String> data = new ArrayList<>();

        private OnItemClickListener<String> clickListener;

        public void setClickListener(OnItemClickListener<String> listener) {
            this.clickListener = listener;
        }

        private final View.OnClickListener viewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView itemNameView = (TextView) v;
                if (clickListener != null) {
                    clickListener.onClick(itemNameView.getText().toString());
                }
            }
        };

        @NonNull
        @Override
        public FuncHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FuncHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_func_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FuncHolder holder, int position) {
            holder.itemName.setText(data.get(position));
            holder.itemName.setOnClickListener(viewClickListener);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setData(List<String> newData) {
            this.data.clear();
            this.data.addAll(newData);
            notifyDataSetChanged();
        }

        static class FuncHolder extends RecyclerView.ViewHolder {

            final TextView itemName;

            public FuncHolder(@NonNull View itemView) {
                super(itemView);
                itemName = itemView.findViewById(R.id.func_item_name);
            }
        }


    }

}
