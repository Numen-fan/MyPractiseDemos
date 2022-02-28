package com.jiajia.mypractisedemos.module.edittextview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.jiajia.mypractisedemos.R;

public class EditTextActivity extends AppCompatActivity {

    private static final String TAG = "EditTextActivity";

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        editText = findViewById(R.id.edt_textview);
    }
}