package com.jiajia.mypractisedemos.module.kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by fanjiajia02 on 2021-07-04
 * Desc: The base activity of all Kotlin activity
 */
open class BaseKotlinActivity : AppCompatActivity() {

    private val activities = ArrayList<BaseKotlinActivity>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "now create ${javaClass.simpleName}")
        activities.add(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        activities.remove(this)

    }

    companion object {
        const val TAG = "BaseKotlinActivity"
    }
}