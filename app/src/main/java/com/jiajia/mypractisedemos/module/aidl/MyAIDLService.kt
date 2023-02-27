package com.jiajia.mypractisedemos.module.aidl

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils

/**
 * 运行在:remote进程中
 */
class MyAIDLService : Service() {

    private val TAG = "MyAIDLService"

    private val binder: Binder = object : IBookAidlInterface.Stub() {
        override fun getTitle(): String {
            return "AIDL->title"
        }

        override fun setTitle(title: String?) {
            LogUtils.warn(TAG, "set Title $title")
        }
    }

    override fun onBind(intent: Intent) = binder
}