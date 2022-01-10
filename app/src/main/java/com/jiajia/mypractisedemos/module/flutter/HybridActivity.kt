package com.jiajia.mypractisedemos.module.flutter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import com.jiajia.mypractisedemos.R
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragment

class HybridActivity : FlutterActivity() {

    private var params: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hybrid)
        params = intent.getStringExtra(PARAMS)

    }

    override fun getInitialRoute(): String? {
        return if (params == null) super.getInitialRoute() else params
    }

    override fun onFlutterUiDisplayed() {
        super.onFlutterUiDisplayed()
        FlutterTools.sendMessageToFlutter("/")
    }


    companion object {

        const val PARAMS = "params"

        fun startActivity(context: Context, params: String) {
            val intent = Intent(context, HybridActivity::class.java)
            intent.putExtra(PARAMS, params)
            context.startActivity(intent)
        }

    }
}