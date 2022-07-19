package com.jiajia.mypractisedemos.module.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by fanjiajia02 on 2022/7/12
 * Desc:
 **/
public class CustomText extends AppCompatTextView {

    String mText;
    Paint mPaint;

    public CustomText(@NonNull Context context) {
        super(context);
    }

    public CustomText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(getTextSize());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mText = getText().toString();

        canvas.save();

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();

        canvas.drawText(mText, 0, Math.abs(fontMetrics.top), mPaint);

        canvas.restore();



    }
}
