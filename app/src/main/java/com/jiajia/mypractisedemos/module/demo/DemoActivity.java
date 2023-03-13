package com.jiajia.mypractisedemos.module.demo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jiajia.basemodule.config.RouteConfig;
import com.jiajia.mypractisedemos.MyApplication;
import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.utils.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

@Route(path = RouteConfig.APP_DEMO_ACTIVITY)
public class DemoActivity extends AppCompatActivity {

    private static final String TAG = "DemoActivity";

    ImageView img;
    Button btn;
    RelativeLayout rlparent;
    ImageView imageView1;
    ImageView imageView2;

    ImageView imgNum;

    LinearLayout llLayout;

    VoipTipsView tipsView;

    View twoImg;
    ImageView imgGrab;

    ViewGroup bigView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin);

        try {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
//                getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            //只支持6.0以上的系统；
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        img = findViewById(R.id.im_header);
        btn = findViewById(R.id.btn_change_margin);
        rlparent = findViewById(R.id.rl_parent);

        tipsView = findViewById(R.id.tips_view);


        imageView1 = findViewById(R.id.img_view1);
        imageView2 = findViewById(R.id.img_view2);
        llLayout = findViewById(R.id.ll_layout);
        imgNum = findViewById(R.id.img_num);
        twoImg = findViewById(R.id.ll_two_img);
        twoImg.setDrawingCacheEnabled(true);
        imgGrab = findViewById(R.id.img_grab);

        btn.setOnClickListener(v -> {
//            rlparent.setVisibility(View.VISIBLE);
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img.getLayoutParams();
//            params.bottomMargin = dp2px(900);
//            img.setLayoutParams(params);


            String microName = "";

            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                AudioDeviceInfo[] audioDeviceInfos = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
                for (AudioDeviceInfo deviceInfo : audioDeviceInfos) {
                    if (deviceInfo.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET
                                        || deviceInfo.getType() == AudioDeviceInfo.TYPE_USB_DEVICE
                                        || deviceInfo.getType() == AudioDeviceInfo.TYPE_USB_HEADSET
                                        || deviceInfo.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES) {
                        microName = deviceInfo.getProductName().toString();
                    } else if (deviceInfo.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_SCO
                                        || deviceInfo.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
                        microName = deviceInfo.getProductName().toString();
                    }

                    if (!TextUtils.isEmpty(microName)) {
                        Toast.makeText(this, microName, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            AnimationDrawable animation=(AnimationDrawable) imageView1.getDrawable();
            Log.d("fjj", animation + "");
            animation.start();

            AnimationDrawable animation2=(AnimationDrawable) imageView2.getDrawable();
            Log.d("fjj", animation2 + "");
            animation2.start();


        });

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(Utils.dp2px(4));
        drawable.setColors(new int[]{Color.parseColor("#665990FF"), Color.parseColor("#335990FF"), Color.parseColor("#665990FF")});
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);

        llLayout.setBackground(drawable);


        imageView2.setOnClickListener(v -> {

            int blue = Color.parseColor("#5990FF");

            VectorDrawable drawable1 = (VectorDrawable) AppCompatResources.getDrawable(this, R.drawable.ic_meeting_template_timer_num_4_g);

            assert drawable1 != null;


            imgNum.setImageDrawable(drawable1);

        });

        imageView1.setOnClickListener(v -> {
//            tipsView.setVisibility(View.VISIBLE);
//            tipsView.load(R.drawable.img_not_corp, R.string.tips_view);

            imgGrab.setImageBitmap(twoImg.getDrawingCache(true));

            // 主线程卡顿10s，会ANR
//            try {
//                Thread.sleep(10_1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            Log.w(TAG, imageView1.hashCode() + "");

        });

        bigView = findViewById(R.id.big_view);


    }
}
