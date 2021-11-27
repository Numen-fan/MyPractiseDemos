package com.jiajia.mypractisedemos.module.seekbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.utils.Utils;

public class ARSeekbarActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView tvProgress;
    private ImageView imgTrack;
    private ConstraintLayout progressTipsLayout;

    private TextThumbSeekBar textThumbSeekBar;

    private IndicatorSeekBar indicatorSeekBar;
    private TextView tvIndicatorValue;

    private int trackValue = 30;

    private final int TRACK_BLUE = Color.parseColor("#5A90FA");
    private final int TRACK_WHITE = Color.parseColor("#FFFFFF");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_seekbar);

        initView();
        initListener();
    }

    private void initView() {
        seekBar = findViewById(R.id.ar_seekbar);
        tvProgress = findViewById(R.id.tv_seekbar_progress);
        imgTrack = findViewById(R.id.img_seekbar_track);
        progressTipsLayout = findViewById(R.id.progress_indicator_layout);
        textThumbSeekBar = findViewById(R.id.textSeekbar);
        indicatorSeekBar = findViewById(R.id.indicator_seekbar);
        tvIndicatorValue = findViewById(R.id.indicator_seekbar_value);
    }

    private void initListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateProgressTipsContent(progress);
                moveProgressIndicator(seekBar, progress);
                updateTrackColor(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progressTipsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progressTipsLayout.setVisibility(View.INVISIBLE);
            }
        });

        indicatorSeekBar.setProgressChangedListener(progress -> tvIndicatorValue.setText(String.valueOf(progress)));
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
        int seekbarWidth = seekBar.getMeasuredWidth();
        int tipsWidth = progressTipsLayout.getMeasuredWidth() / 2;
        int left = progress * (seekbarWidth - Utils.dp2px(20)) / seekBar.getMax()
                + Utils.dp2px(30) + Utils.dp2px(10) - tipsWidth;
        int leftMargin = Math.max(left, 0);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) progressTipsLayout.getLayoutParams();
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
        int thumbOffset = 3; // 当progress>=30时，滑块和刻度点重叠，此时变色导致颜色重叠，增加3个进度的偏移量，避免这一现象
        if (progress >= trackValue + thumbOffset) {
            trackDrawable.setColorFilter(TRACK_BLUE, PorterDuff.Mode.SRC_ATOP);
        } else {
            trackDrawable.setColorFilter(TRACK_WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }
}