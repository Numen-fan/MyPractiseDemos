package com.jiajia.mypractisedemos.module.dialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.jiajia.mypractisedemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogActivity extends AppCompatActivity {

    private static final String TAG = "DialogActivity";
    @BindView(R.id.btn_dialog_style1)
    Button btn_dialog_style1;
    @BindView(R.id.btn_dialog_style2)
    Button btn_dialog_style2;
    @BindView(R.id.btn_dialog_style3)
    Button btn_dialog_style3;
    @BindView(R.id.btn_dialog_style4)
    Button btn_dialog_style4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        ButterKnife.bind(this);


        btn_dialog_style1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DialogActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog
                                        .setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
            }
        });

        btn_dialog_style2.setOnClickListener(v -> {
            CustomDialogActivity.startActivity(this);
        });


    }
}
