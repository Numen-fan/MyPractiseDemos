package com.jiajia.mypractisedemos.module.dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jiajia.mypractisedemos.R;

public class CustomDialogActivity extends AppCompatActivity {

    private static final String TAG = "CustomDialogActivity";

    private CustomDialogFragment mCustomDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        mCustomDialogFragment = new CustomDialogFragment();

        openOrCloseFragment(true);


    }

    public void openOrCloseFragment(boolean open) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment customDialogFragment = fragmentManager.findFragmentByTag(CustomDialogFragment.TAG);

        if (open) {
            if (customDialogFragment == null) {
                fragmentTransaction.add( R.id.container, mCustomDialogFragment, CustomDialogFragment.TAG).commit();
            } else {
                fragmentTransaction.show(customDialogFragment).commit();
            }
        } else {
            if (customDialogFragment == null) {
                return;
            }
            try {
                fragmentTransaction.remove(customDialogFragment).commit();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            finish();
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CustomDialogActivity.class);
        context.startActivity(intent);
    }
}