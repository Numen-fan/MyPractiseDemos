package com.jiajia.mypractisedemos.module.kotlin.util

import android.widget.Toast
import com.jiajia.mypractisedemos.MyApplication

/**
 * Created by fanjiajia02 on 2021-07-04
 * Desc:
 */
object ToastUtils {

    fun showToast(msg: String) {
        Toast.makeText(MyApplication.context, msg, Toast.LENGTH_SHORT).show()
    }

}