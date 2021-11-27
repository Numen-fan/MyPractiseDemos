package com.jiajia.mypractisedemos.module.kotlin

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.jiajia.mypractisedemos.MyApplication

fun String.showToast() {
    Toast.makeText(MyApplication.getInstance(), this, Toast.LENGTH_SHORT).show()
}

/**
 * 长时间的toast，也可以改造上面函数增加默认参数。
 */
fun String.showLongToast() {
    Toast.makeText(MyApplication.getInstance(), this, Toast.LENGTH_LONG).show()
}

/**
 * 适用于字符串资源文件
 */
fun Int.showToast() {
    Toast.makeText(MyApplication.getInstance(), this, Toast.LENGTH_SHORT).show()
}

fun Int.showLongToast() {
    Toast.makeText(MyApplication.getInstance(), this, Toast.LENGTH_LONG).show()
}

/**
 * 对Snackbar进行扩展
 */
fun View.showSnackbar(text: String, actionText: String? = null, duration: Int = Snackbar.LENGTH_SHORT,
                        block: (() -> Unit)? = null) {
    val  snackbar = Snackbar.make(this, text, duration)
    if (actionText != null && block != null) {
        snackbar.setAction(actionText) {
            block()
        }
    }
    snackbar.show()
}

fun View.showSnackbar(text: Int, actionText: Int? = null, duration: Int = Snackbar.LENGTH_SHORT,
                      block: (() -> Unit)? = null) {
    val  snackbar = Snackbar.make(this, text, duration)
    if (actionText != null && block != null) {
        snackbar.setAction(actionText) {
            block()
        }
    }
    snackbar.show()
}