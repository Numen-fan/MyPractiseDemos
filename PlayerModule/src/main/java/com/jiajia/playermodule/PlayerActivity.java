package com.jiajia.playermodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";

    String movieUrl = "http://tv.nousiptv.com:8080/series/223871/Fabian871/1187727.mkv";

    String liveUrl = "http://tv.nousiptv.com:8080/live/223871/Fabian871/1155816.ts";

    String vodUrl = "";

    String url = liveUrl;
    private SurfaceView mSurfaceView;

    IjkMediaPlayer player;

    private Surface surface;

    private int lastVideoWidth;
    private int lastVideoHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        player = new IjkMediaPlayer();

        mSurfaceView = findViewById(R.id.surface_view);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                surface = holder.getSurface();
//                player.setSurface(surface);
//                player.setDisplay(holder);
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

        player.setOnVideoSizeChangedListener((iMediaPlayer, i, i1, i2, i3) -> {
            if (i == lastVideoWidth && i1 == lastVideoHeight) {
                return;
            }
            lastVideoWidth = i;
            lastVideoHeight = i1;
            Log.w(TAG, "i = " + i + ", i1 = " + i1 + ", i2 = " + i2 + ", i3 = " + i3);
            int screenWidth = getScreenWidth(this);
            int videoHeight = screenWidth * i1 / i;
            Log.w(TAG, "screen width is " + screenWidth + ", videoHeight is " + videoHeight);

            ViewGroup.LayoutParams params = mSurfaceView.getLayoutParams();
            params.height = videoHeight;
            mSurfaceView.setLayoutParams(params);
        });

        player.setOnPreparedListener(iMediaPlayer -> {
            if (player != null) {
                Log.w(TAG, "OnPreparedListener callback");
                player.start();
            }
        });

        player.setOnBufferingUpdateListener((iMediaPlayer, i) -> {
//            Log.w(TAG, "Buffering," + i);
        });


//        changePlayerCast(liveUrl);

        findViewById(R.id.btn_live).setOnClickListener(v -> changePlayerCast(liveUrl));

        findViewById(R.id.btn_movie).setOnClickListener(v -> changePlayerCast(movieUrl));

//        findViewById(R.id.btn_vod).setOnClickListener(v->changePlayerCast());

    }

    private void changePlayerCast(String url) {
        if (player == null) {
            return;
        }
        player.reset();
        player.setSurface(surface);
        try {
            player.setDataSource(url);
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void stopPlayer() {
        if (player != null) {
            player.stop();
            player.reset();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null) {
            stopPlayer();
            player = null;
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PlayerActivity.class);
        context.startActivity(intent);
    }

    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm == null ? 0 : wm.getDefaultDisplay().getWidth();
    }
}