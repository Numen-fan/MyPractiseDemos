package com.jiajia.mypractisedemos.module.popwindow;

import android.graphics.drawable.AnimationDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jiajia.mypractisedemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopwindowActivity extends AppCompatActivity {

    @BindView(R.id.btn_pop_test)
    Button btn_pop_test;
    @BindView(R.id.img_pop_image)
    ImageView img_pop_image;
    @BindView(R.id.img_drawable_animation)
    ImageView img_drawable_animation;

    private View mPopView;
    private PopupWindow mPopupWindow;
    private Button btn_pop_ok;
    private Button btn_pop_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow);

        ButterKnife.bind(this);


        initPopWindow();

        initPopView();

        initpopClick();
        // 加载动画资源
        final Animation anim = AnimationUtils.loadAnimation(this,R.anim.tween_anim);
        //设置动画结束后保留结束状态
        anim.setFillAfter(false);

        btn_pop_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopWindow();
                img_pop_image.startAnimation(anim);
            }
        });

        img_drawable_animation.setBackgroundResource(R.drawable.drawableanimation);
        final AnimationDrawable animationDrawable = (AnimationDrawable) img_drawable_animation.getBackground();


        img_drawable_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationDrawable.start();
            }
        });
    }



    /**
     *
     */
    private void initpopClick() {
        btn_pop_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopwindowActivity.this, "OK", Toast.LENGTH_SHORT).show();
            }
        });
        btn_pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopwindowActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     */
    private void initPopView() {
        btn_pop_ok = (Button)mPopView.findViewById(R.id.btn_pop_ok);
        btn_pop_cancel = (Button)mPopView.findViewById(R.id.btn_pop_cancel);
    }

    /**
     *
     */
    private void initPopWindow() {
      mPopView = getLayoutInflater().inflate(R.layout.popwindow, null);
      mPopupWindow = new PopupWindow(mPopView,
              LinearLayout.LayoutParams.WRAP_CONTENT,
              LinearLayout.LayoutParams.WRAP_CONTENT);

      mPopupWindow.setOutsideTouchable(false); // 点击外部不许消失

    };

    /**
     *
     */
    private void showPopWindow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }else {
            mPopupWindow.setAnimationStyle(R.style.pop_animation);
            mPopupWindow.showAtLocation(mPopView, Gravity.BOTTOM, 0,30);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow =null;
        }
    }
}
