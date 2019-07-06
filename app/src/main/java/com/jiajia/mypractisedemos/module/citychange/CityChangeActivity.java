package com.jiajia.mypractisedemos.module.citychange;

import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiajia.mypractisedemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityChangeActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.linear_citychange_view)
    RelativeLayout linearLayout;
    @BindView(R.id.tv_citychange_front)
    TextView front;
    @BindView(R.id.tv_citychange_below)
    TextView below;
    @BindView(R.id.tv_citychange_middle)
    TextView middle;

    private ObjectAnimator animator;
    private ObjectAnimator animator2;
    private ObjectAnimator animator1;

    FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_change);

        ButterKnife.bind(this);

        linearLayout.setOnClickListener(this);

        ft = getSupportFragmentManager().beginTransaction();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_citychange_view:
                // 城市切换
                cityChange();
                break;
            default:
                break;
        }
    }

    private void cityChange() {


        animator = ObjectAnimator.ofFloat(front, "translationX", 0,500,0,0);
        animator.setDuration(1000);
        animator.start();
        animator1 = ObjectAnimator.ofFloat(below, "translationX", 0,-500,0,0);
        animator1.setDuration(1000);
        animator1.start();

        String temp = String.valueOf(front.getText());
        front.setText(String.valueOf(front.getText()));
        below.setText(temp);

        animator2 = ObjectAnimator.ofFloat(middle, "rotation", 0,180,360);
        animator2.setDuration(1000);
        animator2.start();




    }
}
