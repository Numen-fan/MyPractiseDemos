package com.jiajia.mypractisedemos.module.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtils.INSTANCE.showToast("收到电量的通知");
    }
}
