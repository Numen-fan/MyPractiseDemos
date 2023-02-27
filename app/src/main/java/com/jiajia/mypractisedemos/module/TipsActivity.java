package com.jiajia.mypractisedemos.module;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jiajia.mypractisedemos.BaseActivity;
import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;
import com.jiajia.mypractisedemos.utils.Utils;

public class TipsActivity extends BaseActivity {

    private static final String TAG = "TipsActivity";


    View open;


    View settingRoot;
    RadioGroup mRadioGroup;

    RadioButton mRadioBtnOpen;
    RadioButton mRadioBtnClose;
    RadioButton mRadioBtnAuto;

    @Override
    public int getContentResId() {
        return R.layout.activity_tips;
    }

    @Override
    public void initUI() {

        open = findViewById(R.id.tv_open);


        initPhoneCallState();

    }

    @Override
    public void initParam() {

    }

    private void initPhoneCallState() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener listener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String phoneNumber) {

                LogUtils.INSTANCE.error(TAG, "call state = " + state);

                super.onCallStateChanged(state, phoneNumber);

            }
        };

        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void initListener() {
        open.setOnClickListener(v -> openSettingUI());
    }

    private void openSettingUI() {
        if (settingRoot == null) {
            loadSettingView();
        }
        openSettingPage();
    }

    private void  loadSettingView() {
        ViewStub viewStub = findViewById(R.id.view_stub_setting);
        viewStub.inflate();

        settingRoot = findViewById(R.id.setting_root);

        mRadioGroup = findViewById(R.id.radio_group);

        mRadioBtnOpen = findViewById(R.id.radio_open);
        mRadioBtnClose = findViewById(R.id.radio_close);
        mRadioBtnAuto = findViewById(R.id.radio_auto);

        mRadioBtnAuto.setChecked(true);

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> LogUtils.INSTANCE.debug(TAG, "choose " + checkedId));

        mRadioBtnOpen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ToastUtils.INSTANCE.showToast("open");
            }
        });

        mRadioBtnClose.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                closeSettingUI();
            }
        });

        mRadioBtnAuto.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ToastUtils.INSTANCE.showToast("auto");
                LogUtils.INSTANCE.debug(TAG, "auto");
            }
        });

    }

    private void openSettingPage() {
        settingRoot.setVisibility(View.VISIBLE);
        Animation animation = new TranslateAnimation(Utils.getFullScreenWidth(), 0, 0, 0);
        animation.setDuration(200);
        settingRoot.setAnimation(animation);
    }

    private void closeSettingUI() {
        Animation animation = new TranslateAnimation(0, Utils.getFullScreenWidth(), 0, 0);
        animation.setDuration(200);
        settingRoot.setAnimation(animation);
        settingRoot.setVisibility(View.GONE);
    }
}