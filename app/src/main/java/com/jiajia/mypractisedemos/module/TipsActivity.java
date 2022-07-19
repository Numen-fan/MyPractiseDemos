package com.jiajia.mypractisedemos.module;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.module.demo.VoipTipsView;

public class TipsActivity extends AppCompatActivity {

    VoipTipsView tipsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        tipsView = findViewById(R.id.tips_view);

        findViewById(R.id.btn_normal_tips).setOnClickListener(v -> {
            tipsView.setVisibility(View.VISIBLE);
        });

    }
}