package com.jiajia.mypractisedemos.module.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.jiajia.mypractisedemos.utils.Utils;

/**
 * Created by fanjiajia02 on 2021-06-21
 * Desc:
 */
public class TextThumbSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    private final int mThumbSize;//绘制滑块宽度
    private final TextPaint mTextPaint;//绘制文本的大小
    private int mSeekBarMin=0;//滑块开始值

    public TextThumbSeekBar(Context context) {
        this(context, null);
    }

    public TextThumbSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public TextThumbSeekBar(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        mThumbSize=Utils.dp2px(20);
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(Utils.dp2px(16));
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int unsignedMin = mSeekBarMin < 0 ? mSeekBarMin * -1 : mSeekBarMin;
        String progressText = String.valueOf(getProgress()+unsignedMin);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(progressText, 0, progressText.length(), bounds);

        int leftPadding = getPaddingLeft() - getThumbOffset();
        int rightPadding = getPaddingRight() - getThumbOffset();
        int width = getWidth() - leftPadding - rightPadding;
        float progressRatio = (float) getProgress() / getMax();
        float thumbOffset = mThumbSize * (.5f - progressRatio);
        float thumbX = progressRatio * width + leftPadding + thumbOffset;
        float thumbY = getHeight() / 2f + bounds.height() / 2f;
        canvas.drawText(progressText, thumbX, thumbY, mTextPaint);
    }

    public void setMix(int min){
        mSeekBarMin=min;
    }
}
