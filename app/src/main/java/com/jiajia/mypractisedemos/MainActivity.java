package com.jiajia.mypractisedemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btn_recyc_decoration)
    Button btn_recyc_decoration;
    @BindView(R.id.btn_recyc_fzxt)
    Button btn_recyc_fzxt;
    @BindView(R.id.btn_start_wheel)
    Button btn_start_wheel;
    @BindView(R.id.btn_train)
    Button btn_train;
    @BindView(R.id.btn_city)
    Button btn_citychange;
    @BindView(R.id.btn_picturescale)
    Button btn_picturescale;
    @BindView(R.id.btn_mvp)
    Button btn_mvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btn_recyc_decoration.setOnClickListener(this);
        btn_recyc_fzxt.setOnClickListener(this);
        btn_start_wheel.setOnClickListener(this);
        btn_train.setOnClickListener(this);
        btn_citychange.setOnClickListener(this);
        btn_picturescale.setOnClickListener(this);
        btn_mvp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String activity = "";
        switch (v.getId()) {
            case R.id.btn_recyc_fzxt:
                activity = "com.jiajia.mypractisedemos.module.recycgroup1.RecycGroup1Activity";
                break;
            case R.id.btn_recyc_decoration:
                activity = "com.jiajia.mypractisedemos.module.decoration.DecorationActivity";
                break;
            case R.id.btn_start_wheel:
                activity = "com.jiajia.mypractisedemos.module.wheeldialog.WheelActivity";
                break;
            case R.id.btn_train:
                activity = "com.jiajia.mypractisedemos.module.trainrecyclerview.Trainrecyclerview";
                break;
            case R.id.btn_city:
                activity = "com.jiajia.mypractisedemos.module.citychange.CityChangeActivity";
                break;
            case R.id.btn_picturescale:
                activity = "com.jiajia.mypractisedemos.module.picturescale.PictureScaleActivity";
                break;
            case R.id.btn_mvp:
                activity = "com.jiajia.mypractisedemos.module.mvpdemo.LoginMvpActivity";
            default:
                break;
        }
        Intent intent1 = null;
        try {
            intent1 = new Intent(MainActivity.this, Class.forName(activity));
            startActivity(intent1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
