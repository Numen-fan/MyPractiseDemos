package com.jiajia.mypractisedemos.module.videocompressor;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.jiajia.mypractisedemos.databinding.ActivityVideoCompressorBinding;

public class VideoCompressorActivity extends AppCompatActivity {

    private ActivityVideoCompressorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVideoCompressorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}