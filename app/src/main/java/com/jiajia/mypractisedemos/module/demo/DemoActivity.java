package com.jiajia.mypractisedemos.module.demo;

import com.jiajia.mypractisedemos.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DemoActivity extends AppCompatActivity {

    ImageView img;
    Button btn;
    RelativeLayout rlparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin);

        try {
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
//                getWindow().setNavigationBarColor(Color.TRANSPARENT);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
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

        btn.setOnClickListener(v -> {
            rlparent.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img.getLayoutParams();
            params.bottomMargin = dp2px(900);
            img.setLayoutParams(params);


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


        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }

    public int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
