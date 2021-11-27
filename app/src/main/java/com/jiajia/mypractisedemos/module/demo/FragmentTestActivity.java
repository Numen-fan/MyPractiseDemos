package com.jiajia.mypractisedemos.module.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewStub;
import android.widget.Button;

import com.jiajia.mypractisedemos.R;

public class FragmentTestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    Fragment fragment;

    Button btnInflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "fjjj, onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);


        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(BlankFragment.TAG) == null) {
            Log.d(TAG, "fjjj, fragment is null");
            fragment = new BlankFragment();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.fragment_container, fragment, BlankFragment.TAG);
            ft.commit();
        }

        btnInflate = findViewById(R.id.btn_inflate);

        btnInflate.setOnClickListener( (v) -> {
            ViewStub stub = findViewById(R.id.view_stub);
            stub.inflate();
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "fjjj, onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "fjjj, onDestroy");
    }
}