package com.jiajia.mypractisedemos.module.wheeldialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiajia.mypractisedemos.R;
import com.wx.wheelview.widget.WheelViewDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WheelActivity extends AppCompatActivity {

//    @BindView(R.id.btn_start)
//    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);

        ButterKnife.bind(this);

//        btn_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//            }
//        });


    }
    public void showDialog(View view) {
        WheelViewDialog dialog = new WheelViewDialog(this);
        dialog.setTitle("wheelview dialog")
                .setItems(createArrays())
                .setButtonText("确定")
                .setDialogStyle(Color.parseColor("#6699ff"))
                .setCount(5)
                .show();
    }

    private ArrayList<String> createArrays() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
        }
        return list;
    }

}
