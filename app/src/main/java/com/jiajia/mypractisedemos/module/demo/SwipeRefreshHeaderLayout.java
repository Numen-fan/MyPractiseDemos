package com.jiajia.mypractisedemos.module.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;

import com.jiajia.mypractisedemos.R;

public class SwipeRefreshHeaderLayout extends FrameLayout
        implements SwipeToLoadLayout.SwipeRefreshTrigger, SwipeToLoadLayout.SwipeTrigger {


    TextView tvTips;
    ImageView ivLoading;
    RotateAnimation rotateAnimation;

    public int loadResult = SUCCESS;


    public SwipeRefreshHeaderLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRefreshHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTips = findViewById(R.id.tv_header_hint);
        tvTips.setText("下拉加载");
        ivLoading = findViewById(R.id.iv_loading);
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    @Override
    public void onRefresh() {
        ivLoading.setVisibility(VISIBLE);
        ivLoading.setAnimation(rotateAnimation);
        tvTips.setText("下拉加载");
    }

    @Override
    public void onPrepare() {
        tvTips.setText("下拉加载");
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        ivLoading.clearAnimation();
        ivLoading.setVisibility(GONE);
        switch (loadResult) {
            case FAILED:
                tvTips.setText("加载失败");
                break;
            case NO_MORE:
                tvTips.setText("没有更多");
                break;
            default:
                tvTips.setText("加载完成");
                break;
        }
    }

    @Override
    public void onReset() {
        ivLoading.clearAnimation();
        ivLoading.setVisibility(GONE);
        tvTips.setText("下拉加载");
    }

    public static final int SUCCESS = 1;
    public static final int FAILED = 2;
    public static final int NO_MORE = 3;

    @IntDef({ SUCCESS, FAILED, NO_MORE })
    public @interface LoadResult {
    }

}
