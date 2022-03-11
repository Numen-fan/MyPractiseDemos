package com.jiajia.mypractisedemos;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jiajia.mypractisedemos.module.audio.AudioActivity;
import com.jiajia.mypractisedemos.module.citychange.CityChangeActivity;
import com.jiajia.mypractisedemos.module.compose.ComposeMainActivity;
import com.jiajia.mypractisedemos.module.decoration.DecorationActivity;
import com.jiajia.mypractisedemos.module.demo.DemoActivity;
import com.jiajia.mypractisedemos.module.dialog.DialogActivity;
import com.jiajia.mypractisedemos.module.edittextview.EditTextActivity;
import com.jiajia.mypractisedemos.module.expendablelayout.ExpendableLayoutActivity;
import com.jiajia.mypractisedemos.module.floatwindow.FloatView;
import com.jiajia.mypractisedemos.module.jetpack.JetpackActivity;
import com.jiajia.mypractisedemos.module.kotlin.activity.KotlinActivity;
import com.jiajia.mypractisedemos.module.manfunctionsui.ManyFunctionUIActivity;
import com.jiajia.mypractisedemos.module.mvpdemo.view.LoginMvpActivity;
import com.jiajia.mypractisedemos.module.mylinearlayout.MyLinearLayoutActivity;
import com.jiajia.mypractisedemos.module.picturescale.PictureScaleActivity;
import com.jiajia.mypractisedemos.module.popwindow.PopwindowActivity;
import com.jiajia.mypractisedemos.module.recycgroup1.RecycGroup1Activity;
import com.jiajia.mypractisedemos.module.seekbar.SeekBarActivity;
import com.jiajia.mypractisedemos.module.trainrecyclerview.Trainrecyclerview;
import com.jiajia.mypractisedemos.module.wheeldialog.WheelActivity;
import com.jiajia.mypractisedemos.module.widgetdemo.WidgetDemoActivity;
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
    @BindView(R.id.btn_kotlin)
    Button btn_kotlin;
    @BindView(R.id.btn_edit_textview)
    Button btn_edit_textview;
    @BindView(R.id.btn_compose)
    Button btn_compose;


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
        btn_kotlin.setOnClickListener(this);
        btn_edit_textview.setOnClickListener(this);
        btn_compose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recyc_fzxt:
                BaseActivity.startActivity(this, RecycGroup1Activity.class);
                break;
            case R.id.btn_recyc_decoration:
                BaseActivity.startActivity(this, DecorationActivity.class);
                break;
            case R.id.btn_start_wheel:
                BaseActivity.startActivity(this, WheelActivity.class);
                break;
            case R.id.btn_train:
                BaseActivity.startActivity(this, Trainrecyclerview.class);
                break;
            case R.id.btn_city:
                BaseActivity.startActivity(this, CityChangeActivity.class);
                break;
            case R.id.btn_picturescale:
                BaseActivity.startActivity(this, PictureScaleActivity.class);
                break;
            case R.id.btn_mvp:
                BaseActivity.startActivity(this, LoginMvpActivity.class);
                break;
            case R.id.btn_expendable:
                BaseActivity.startActivity(this, ExpendableLayoutActivity.class);
                break;
            case R.id.btn_manyFunction:
                BaseActivity.startActivity(this, ManyFunctionUIActivity.class);
                break;
            case R.id.btn_popWindow:
                BaseActivity.startActivity(this, PopwindowActivity.class);
                break;
            case R.id.btn_dialog:
                BaseActivity.startActivity(this, DialogActivity.class);
                break;
            case R.id.btn_jetpack:
                BaseActivity.startActivity(this, JetpackActivity.class);
                break;
            case R.id.btn_mylinearlayout:
                BaseActivity.startActivity(this, MyLinearLayoutActivity.class);
                break;
            case R.id.btn_demo:
                BaseActivity.startActivity(this, DemoActivity.class);
                break;
            case R.id.btn_seekbar:
                BaseActivity.startActivity(this, SeekBarActivity.class);
                break;
            case R.id.btn_audio:
                BaseActivity.startActivity(this, AudioActivity.class);
                break;
            case R.id.btn_float_window:
                handleClickEvent(R.id.btn_float_window);
                break;
            case R.id.btn_self_wigdet:
                BaseActivity.startActivity(this, WidgetDemoActivity.class);
                break;
            case R.id.btn_kotlin:
                BaseActivity.startActivity(this, KotlinActivity.class);
                break;
            case R.id.btn_edit_textview:
                BaseActivity.startActivity(this, EditTextActivity.class);
                break;
            case R.id.btn_compose:
                BaseActivity.startActivity(this, ComposeMainActivity.class);
                break;
            default:
                break;
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
