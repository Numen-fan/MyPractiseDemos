package com.jiajia.mypractisedemos.module.seekbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.utils.Utils;

/**
 * Created by fanjiajia02 on 2021-06-22
 * Desc: A SeekBar with moving indicator
 */
public class IndicatorSeekBar extends FrameLayout {

    private Context context;

    private SeekBar seekBar;
    private TextView tvProgress;
    private ImageView imgTrack;
    private ImageView imgTipsArrow;
    private ConstraintLayout progressTipsLayout;

    private final int trackValue = 30; // SeekBar上的刻度

    /* 刻度点颜色 */
    private final int TRACK_BLUE = Color.parseColor("#5A90FA");
    private final int TRACK_WHITE = Color.parseColor("#FFFFFF");

    private OnProgressChangedListener mProgressChangedListener;

    public IndicatorSeekBar(@NonNull Context context) {
        super(context);
    }

    public IndicatorSeekBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUI();
        initListener();
        initThumb();
        initTrack();
    }



    private void initUI() {
        View rootView = LayoutInflater.from(context).inflate(R.layout.indicator_seekbar_layout,this);
        seekBar = rootView.findViewById(R.id.ar_seekbar);
        imgTrack = rootView.findViewById(R.id.img_seekbar_track);
        imgTipsArrow = rootView.findViewById(R.id.img_tips_arrow);
        tvProgress = rootView.findViewById(R.id.tv_seekbar_progress);
        progressTipsLayout = rootView.findViewById(R.id.progress_indicator_layout);
    }

    private void initThumb() {
        seekBar.setThumb(getThumbAndTrackDrawable(true));
        seekBar.setThumbOffset(0);
    }

    private void initTrack() {
        imgTrack.setBackground(getThumbAndTrackDrawable(false));
    }

    private Drawable getThumbAndTrackDrawable(boolean isThumb) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(TRACK_WHITE);
        if (isThumb) {
            drawable.setSize(Utils.dp2px(16), Utils.dp2px(16));
            drawable.setStroke(Utils.dp2px(1.33f), TRACK_BLUE);
        } else {
            drawable.setSize(Utils.dp2px(5), Utils.dp2px(5));
        }
        return drawable;
    }


    private void initListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateProgressTipsContent(progress);
                moveProgressIndicator(seekBar, progress);
                updateTrackColor(progress);
                updateTipsStyle(progress);
                if (mProgressChangedListener != null) {
                    mProgressChangedListener.onProgressChanged(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                showOrHideTipsLayout(true);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                showOrHideTipsLayout(false);
            }
        });
    }


    /**
     * Control the Visibility for above tips layout
     * show tips when {@param show} is true, otherwise hide.
     */
    private void showOrHideTipsLayout(boolean show) {
        progressTipsLayout.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    /**
     * set progress value
     */
    private void updateProgressTipsContent(int progress) {
        tvProgress.setText(String.valueOf(progress));
    }

    /**
     * Change indicator's location
     */
    private void moveProgressIndicator(SeekBar seekBar, int progress) {
        int seekBarWidth = seekBar.getMeasuredWidth();
        ConstraintLayout.LayoutParams seekBarParams
                = (ConstraintLayout.LayoutParams) seekBar.getLayoutParams();
        int seekBarMarginLeft = seekBarParams.leftMargin;
        int tipsHalfWidth = progressTipsLayout.getMeasuredWidth() / 2;
        int thumbWidth = seekBar.getThumb().getBounds().width(); // 滑块的宽度
        // 计算tips布局的leftMargin值
        int left = progress * (seekBarWidth - thumbWidth) / seekBar.getMax()
                + seekBarMarginLeft - tipsHalfWidth + thumbWidth / 2 ;
        int leftMargin = Math.max(left, 0);

        ConstraintLayout.LayoutParams params
                = (ConstraintLayout.LayoutParams) progressTipsLayout.getLayoutParams();
        params.leftMargin = leftMargin;
        progressTipsLayout.setLayoutParams(params);
    }

    /**
     * Change track's color
     */
    private void updateTrackColor(int progress) {
        if (!(imgTrack.getBackground() instanceof GradientDrawable)) {
            return;
        }
        GradientDrawable trackDrawable = (GradientDrawable) imgTrack.getBackground();
        int thumbOffset = 3; // 当progress>=30时，滑块和刻度点重叠，立刻变色导致颜色重叠，增加3个进度的偏移量，避免这一现象
        int targetColor = progress >= trackValue + thumbOffset ? TRACK_BLUE : TRACK_WHITE;
        trackDrawable.setColor(targetColor);
    }

    /**
     * Change the above tips layout style
     */
    private void updateTipsStyle(int progress) {
        int iconResId = progress == trackValue ? R.mipmap.icon_indicator_seekbar_arrow
                : R.mipmap.icon_indicator_seekbar_white_arrow;
        imgTipsArrow.setBackground(ContextCompat.getDrawable(getContext(), iconResId));
        tvProgress.setTextColor(progress == trackValue ? TRACK_WHITE : TRACK_BLUE);
        GradientDrawable bgDrawable = (GradientDrawable) tvProgress.getBackground();
        int targetColor = progress == trackValue ? TRACK_BLUE : TRACK_WHITE;
        bgDrawable.setColor(targetColor);
    }

    public void setProgressChangedListener(OnProgressChangedListener l) {
        this.mProgressChangedListener = l;
    }

    public void setProgressInitValue(int initValue) {
        if (initValue < 0 || initValue > seekBar.getMax()) {
            // TODO: 6/22/21 log
            return;
        }
        seekBar.setProgress(initValue);
    }

    /**
     * Notify {@link #mProgressChangedListener} when the SeekBar's value changed
     */
    public interface OnProgressChangedListener {

        void onProgressChanged(int progress);
    }
}
