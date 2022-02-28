package com.jiajia.mypractisedemos.module.flutter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class HybridActivity : FlutterActivity() {

    private var params: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.debug(TAG, "onCreate")
        params = intent.getStringExtra(PARAMS)
    }

    override fun getInitialRoute(): String? {
        LogUtils.debug(TAG, "getInitialRoute")
        return if (params == null) super.getInitialRoute() else params
    }

    override fun onStart() {
        super.onStart()
        FlutterTools.sendMessageToFlutter("你好，我是Android");
    }

    override fun provideFlutterEngine(context: Context): FlutterEngine? {
        LogUtils.debug(TAG, "provideFlutterEngine")
       return FlutterTools.getFlutterEngine(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        FlutterTools.destroyEngine()
    }


    companion object {

        private const val TAG = "HybridActivity"

        const val PARAMS = "params"

        fun startActivity(context: Context, params: String) {
            val intent = Intent(context, HybridActivity::class.java)
            intent.putExtra(PARAMS, params)
            context.startActivity(intent)
        }

    }
}