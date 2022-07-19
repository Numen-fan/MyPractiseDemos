package com.jiajia.mypractisedemos.module.demo;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by fanjiajia02 on 2022/7/12
 * Desc:
 **/
public class ColorTrackTextView extends AppCompatTextView {

    private String TAG = "ColorTrackTextView";

    // 默认的字体颜色的画笔
    private Paint mOriginPaint;
    // 改变的字体颜色的画笔
    private Paint mChangePaint;
    // 当前的进度
    private float mCurrentProgress = 0.6f;

    // 当前文本
    private String mText;


    public ColorTrackTextView(@NonNull Context context) {
        super(context);
    }

    public ColorTrackTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ColorTrackTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mOriginPaint = getPaintByColor(Color.WHITE);
        mChangePaint = getPaintByColor(Color.RED);
    }

    /**
     * 获取画笔根据不同的颜色
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
        // 仿抖动
        paint.setDither(true);
        paint.setColor(color);
        // 字体的大小设置为TextView的文字大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    /**
     * 这里肯定是自己去画  不能用super
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // 获取当前文本
        mText = getText().toString();
        // 获取控件宽度
        int width = getWidth();
        if (!TextUtils.isEmpty(mText)) {
            // 根据当前进度计算中间位置
            int middle = (int) (width * mCurrentProgress);
            drawOrigin(canvas, middle);
            drawChange(canvas, middle);
        }
    }
    /**
     * 画变色的字体部分
     */
    private void drawChange(Canvas canvas, int middle) {
        drawText(canvas, mChangePaint, 0, middle);
    }

    /**
     * 画不变色的字体部分
     */
    private void drawOrigin(Canvas canvas, int middle) {
        drawText(canvas, mOriginPaint, middle, getWidth());
    }

    /**
     * 绘制文本根据指定的位置
     *
     * @param canvas canvas画布
     * @param paint  画笔
     * @param startX 开始的位置
     * @param endX   结束的位置
     */
    private void drawText(Canvas canvas, Paint paint, int startX, int endX) {
        // 保存画笔状态
        canvas.save();
        // 截取绘制的内容，待会就只会绘制clipRect设置的参数部分
        canvas.clipRect(startX, 0, endX, getHeight());
        // 获取文字的范围
        Rect bounds = new Rect(); // 这个bounds是相对baseline的一个矩阵，注意坐标值
        mOriginPaint.getTextBounds(mText, 0, mText.length(), bounds);
        // 获取文字的Metrics 用来计算基线
        Paint.FontMetricsInt fontMetrics = mOriginPaint.getFontMetricsInt();
        // 获取文字的宽高
        int fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        // 计算基线到中心点的位置
        int offY = fontTotalHeight / 2 - fontMetrics.bottom;
        // 计算基线位置
        int baseline = Math.abs(fontMetrics.top);
        canvas.drawText(mText, getMeasuredWidth() / 2 - bounds.width() / 2, baseline, paint);
        // 释放画笔状态
        canvas.restore();
    }


}
