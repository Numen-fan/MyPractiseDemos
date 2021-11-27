package com.jiajia.mypractisedemos.module.kotlin.util

import android.util.Log
import java.lang.Exception

/**
 * Created by fanjiajia02 on 2021-07-04
 * Desc: 日志输出类，后续加上日志输出
 */
object LogUtils {

    fun debug(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun info(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun warn(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    fun error(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun error(tag: String, msg: String, e: Exception) {
        Log.e(tag, msg + "\n\t" + e.message)
    }

    fun error(tag: String, msg: String, t: Throwable) {
        Log.e(tag, msg + "\n\t" + t.message)
    }

}