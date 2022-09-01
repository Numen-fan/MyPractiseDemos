package com.jiajia.kotlinmodule.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jiajia.kotlinmodule.KotlinModuleApplication
import com.jiajia.kotlinmodule.R

/**
 * Created by fanjiajia02 on 2021-04-10
 * Desc:
 */
class BasisView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 50f
        canvas?.drawCircle(90f, 200f, 150f, paint)

        Log.d(TAG, "onDraw " + KotlinModuleApplication.getInstance().resources.getString(R.string.kotlin_module_name))
    }

    companion object {
        const val TAG = "BasisView"
    }

}