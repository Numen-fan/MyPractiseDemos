package com.example.kotlinsupportlib

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

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
    }

}