package com.jiajia.mypractisedemos.module.mylinearlayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.jiajia.mypractisedemos.R;

public class MyLinearLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_linear_layout);

        MyLinearLayout myLinearLayout1 = findViewById(R.id.mylinear_test1);
        MyLinearLayout myLinearLayout2= findViewById(R.id.mylinear_test2);

        myLinearLayout1.setContent("食品");

        myLinearLayout1.setOnClickListener(v -> {
            myLinearLayout1.setData("米饭");
            myLinearLayout1.showDividerView(false);
        });
        myLinearLayout2.setOnClickListener(v -> {
            myLinearLayout1.showDividerView(true);
        });

    }
}
