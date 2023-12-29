package com.jiajia.playermodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";

    String url = "http://tv.nousiptv.com:8080/movie/223871/Fabian871/1118283.mkv";
    private SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        IjkMediaPlayer player = new IjkMediaPlayer();

        mSurfaceView = findViewById(R.id.surface_view);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                player.setDisplay(holder);
                Log.d(TAG, "surfaceCreated: ");
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed: ");
            }
        });

        try {
            player.setDataSource(url);
            player.prepareAsync();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PlayerActivity.class);
        context.startActivity(intent);
    }
}