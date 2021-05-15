package com.jiajia.mypractisedemos;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jiajia.mypractisedemos.module.floatwindow.FloatView;
import com.jiajia.mypractisedemos.utils.PermissionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.btn_recyc_decoration)
    Button btn_recyc_decoration;
    @BindView(R.id.btn_recyc_fzxt)
    Button btn_recyc_fzxt;
    @BindView(R.id.btn_start_wheel)
    Button btn_start_wheel;
    @BindView(R.id.btn_train)
    Button btn_train;
    @BindView(R.id.btn_city)
    Button btn_citychange;
    @BindView(R.id.btn_picturescale)
    Button btn_picturescale;
    @BindView(R.id.btn_mvp)
    Button btn_mvp;
    @BindView(R.id.btn_expendable)
    Button btn_expendable;
    @BindView(R.id.btn_manyFunction)
    Button btn_manyFunction;
    @BindView(R.id.btn_popWindow)
    Button btn_popWindow;
    @BindView(R.id.btn_dialog)
    Button btn_dialog;
    @BindView(R.id.btn_jetpack)
    Button btn_jetpack;
    @BindView(R.id.btn_mylinearlayout)
    Button btn_mylinearlayout;
    @BindView(R.id.btn_demo)
    Button btn_demo;
    @BindView(R.id.btn_seekbar)
    Button btn_seekbar;
    @BindView(R.id.btn_audio)
    Button btn_audio;
    @BindView(R.id.btn_float_window)
    Button btn_float_window;
    @BindView(R.id.btn_live_replay)
    Button btn_live_replay;
    @BindView(R.id.btn_self_wigdet)
    Button btn_self_wigdet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btn_recyc_decoration.setOnClickListener(this);
        btn_recyc_fzxt.setOnClickListener(this);
        btn_start_wheel.setOnClickListener(this);
        btn_train.setOnClickListener(this);
        btn_citychange.setOnClickListener(this);
        btn_picturescale.setOnClickListener(this);
        btn_mvp.setOnClickListener(this);
        btn_expendable.setOnClickListener(this);
        btn_manyFunction.setOnClickListener(this);
        btn_popWindow.setOnClickListener(this);
        btn_dialog.setOnClickListener(this);
        btn_jetpack.setOnClickListener(this);
        btn_mylinearlayout.setOnClickListener(this);
        btn_demo.setOnClickListener(this);
        btn_seekbar.setOnClickListener(this);
        btn_audio.setOnClickListener(this);
        btn_float_window.setOnClickListener(this);
        btn_live_replay.setOnClickListener(this);
        btn_self_wigdet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String activity = "";
        switch (v.getId()) {
            case R.id.btn_recyc_fzxt:
                activity = "com.jiajia.mypractisedemos.module.recycgroup1.RecycGroup1Activity";
                break;
            case R.id.btn_recyc_decoration:
                activity = "com.jiajia.mypractisedemos.module.decoration.DecorationActivity";
                break;
            case R.id.btn_start_wheel:
                activity = "com.jiajia.mypractisedemos.module.wheeldialog.WheelActivity";
                break;
            case R.id.btn_train:
                activity = "com.jiajia.mypractisedemos.module.trainrecyclerview.Trainrecyclerview";
                break;
            case R.id.btn_city:
                activity = "com.jiajia.mypractisedemos.module.citychange.CityChangeActivity";
                break;
            case R.id.btn_picturescale:
                activity = "com.jiajia.mypractisedemos.module.picturescale.PictureScaleActivity";
                break;
            case R.id.btn_mvp:
                activity = "com.jiajia.mypractisedemos.module.mvpdemo.view.LoginMvpActivity";
                break;
            case R.id.btn_expendable:
                activity = "com.jiajia.mypractisedemos.module.expendablelayout.ExpendableLayoutActivity";
                break;
            case R.id.btn_manyFunction:
                activity = "com.jiajia.mypractisedemos.module.manfunctionsui.ManyFunctionUIActivity";
                break;
            case R.id.btn_popWindow:
                activity = "com.jiajia.mypractisedemos.module.popwindow.PopwindowActivity";
                break;
            case R.id.btn_dialog:
                activity = "com.jiajia.mypractisedemos.module.dialog.DialogActivity";
                break;
            case R.id.btn_jetpack:
                activity = "com.jiajia.mypractisedemos.module.jetpack.JetpackActivity";
                break;
            case R.id.btn_mylinearlayout:
                activity = "com.jiajia.mypractisedemos.module.mylinearlayout.MyLinearLayoutActivity";
                break;
            case R.id.btn_demo:
                activity = "com.jiajia.mypractisedemos.module.demo.DemoActivity";
                break;
            case R.id.btn_seekbar:
                activity = "com.jiajia.mypractisedemos.module.seekbar.SeekBarActivity";
                break;
            case R.id.btn_audio:
                activity = "com.jiajia.mypractisedemos.module.audio.AudioActivity";
                break;
            case R.id.btn_float_window:
                handleClickEvent(R.id.btn_float_window);
                break;
            case R.id.btn_self_wigdet:
                activity = "com.jiajia.mypractisedemos.module.widgetdemo.WidgetDemoActivity";
            default:
                break;
        }
        Intent intent1;
        try {
            intent1 = new Intent(MainActivity.this, Class.forName(activity));
            startActivity(intent1);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "startActivity error,", e);
            Toast.makeText(this, "start activity error!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 在主界面处理不需要启动相关Activity的点击事件
     *
     * @param resId
     */
    private void handleClickEvent(final int resId) {
        switch (resId) {
            case R.id.btn_float_window:
                launchFloatWindow();
                break;
            default:
                break;
        }
    }

    /**
     * 悬浮窗
     */
    private void launchFloatWindow() {
        if (PermissionUtil.canDrawOverlays(this)) {
//            FloatWindow.getInstance().show();
            FloatView floatView = new FloatView(this); // 创建窗体
            floatView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
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
}
