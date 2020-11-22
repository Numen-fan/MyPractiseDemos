package com.jiajia.mypractisedemos.module.seekbar;

import com.jiajia.mypractisedemos.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.widget.Button;
import android.widget.SeekBar;

public class SeekBarActivity extends AppCompatActivity {

    Button btnAdd;
    Button btnReduce;
    SeekBar bar;
    int width = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);

        btnAdd = findViewById(R.id.btn_add_progress);
        btnReduce = findViewById(R.id.btn_reduce_progress);
        bar = findViewById(R.id.seekbar_hitv_gallery_switch);

        StateListDrawable drawable1 = (StateListDrawable) getDrawable(R.drawable.hitv_progress_middle_icon_selecter);

        btnAdd.setOnClickListener(v -> {

            GradientDrawable shapeDrawable = new GradientDrawable();
            shapeDrawable.setShape(GradientDrawable.RECTANGLE);
            shapeDrawable.setCornerRadius(dp2px(11));
            shapeDrawable.setColor(Color.parseColor("#5A90FA"));
            shapeDrawable.setSize(dp2px(width +=10), dp2px(22));
            shapeDrawable.setStroke(dp2px(2), Color.parseColor("#4170CC"));

            BitmapDrawable drawableIcon = (BitmapDrawable)getDrawable(R.mipmap.hitv_progress_icon);
            drawableIcon.setGravity(Gravity.CENTER);

            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shapeDrawable,drawableIcon});

            bar.setThumb(layerDrawable);
            bar.setThumbOffset(0);

            btnAdd.animate().translationX(100).setDuration(500);
        });

        btnReduce.setOnClickListener(v -> {
//            if (width == 10) {
//                return;
//            }
//            GradientDrawable shapeDrawable = new GradientDrawable();
//            shapeDrawable.setShape(GradientDrawable.RECTANGLE);
//            shapeDrawable.setCornerRadius(dp2px(11));
//            shapeDrawable.setColor(Color.parseColor("#5A90FA"));
//            shapeDrawable.setSize(dp2px(width -= 10), dp2px(22));
//            shapeDrawable.setStroke(dp2px(2), Color.parseColor("#4170CC"));
//
//            BitmapDrawable drawableIcon = (BitmapDrawable)getDrawable(R.mipmap.hitv_progress_icon);
//            drawableIcon.setGravity(Gravity.CENTER);
//
//            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shapeDrawable,drawableIcon});
//
//            bar.setThumb(layerDrawable);
//            bar.setThumbOffset(0);

            btnAdd.animate().translationX(0).setDuration(500);;


        });
    }

    public int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private class MyGestureListner extends GestureDetector {

        public MyGestureListner(Context context,
                                OnGestureListener listener) {
            super(context, listener);
        }

        public MyGestureListner(Context context, OnGestureListener listener, Handler handler) {
            super(context, listener, handler);
        }

        public MyGestureListner(Context context, OnGestureListener listener, Handler handler,
                                boolean unused) {
            super(context, listener, handler, unused);
        }

    }

}


