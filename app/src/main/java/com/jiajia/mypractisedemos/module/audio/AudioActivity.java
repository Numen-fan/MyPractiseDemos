package com.jiajia.mypractisedemos.module.audio;

import com.jiajia.mypractisedemos.R;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class AudioActivity extends AppCompatActivity {

    private static final String TAG = "AudioActivity";

    private Button btnPlay;
    private Button btnStartBluetooth;
    private Button btnStopBluetooth;
    private Button btnSwitchBluetoothOn;

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        // 播放器设置
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(AudioActivity.this,
                    Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.music));
        } catch (Exception e) {
            Log.e(TAG, "error");
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
        mediaPlayer.setLooping(true);
        mediaPlayer.prepareAsync();

        initView();
        initListener();
        iniAudioManager();
        initBroadcaseReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void iniAudioManager() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager == null) {
            return;
        }
//        resetSco();
    }

    private void resetSco() {
        mAudioManager.setMode(AudioManager.MODE_NORMAL);
        mAudioManager.stopBluetoothSco();
        mAudioManager.setBluetoothScoOn(false);
        mAudioManager.setSpeakerphoneOn(false);
    }

    private void initView() {
        btnPlay = findViewById(R.id.btn_audio_play);
        btnStopBluetooth = findViewById(R.id.btn_audio_stop_bluetooth);
        btnStartBluetooth = findViewById(R.id.btn_audio_start_bluetooth);
        btnSwitchBluetoothOn = findViewById(R.id.btn_audio_set_bluetoothOn);
    }

    private void initListener() {
        btnPlay.setOnClickListener((view) -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener((MediaPlayer mp) -> mediaPlayer.seekTo(0));

        mediaPlayer.setOnPreparedListener((v) -> {
//            mediaPlayer.start();
        });

        mediaPlayer.setOnErrorListener((MediaPlayer mp, int what, int extra) -> {
                    Log.e(TAG, what + "," + extra);
                    return false;
                }
        );

        btnStartBluetooth.setOnClickListener(v -> {
            if (mAudioManager == null || !isBlueToothHeadsetConnected()) {
                return;
            }
            if (!mAudioManager.isBluetoothScoAvailableOffCall()) {
                Log.d(TAG, "current platform is not support use of bluetooth SCO");
            }
            Log.d(TAG, "go to startBluetoothSco");
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION); //模拟设置为通话 声音从听筒输出
            mAudioManager.stopBluetoothSco();  // 每次start之前先stop一次
            mAudioManager.startBluetoothSco();
            mAudioManager.setSpeakerphoneOn(false); // 关闭扬声器
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL); // 设置声音调节为 通话
        });

        btnStopBluetooth.setOnClickListener(v -> {
            mAudioManager.setMode(AudioManager.MODE_NORMAL); // 恢复
            setVolumeControlStream(AudioManager.STREAM_SYSTEM); // 恢复音量控制
            mAudioManager.stopBluetoothSco();
        });

        btnSwitchBluetoothOn.setOnClickListener((v) -> {
            mAudioManager.setBluetoothScoOn(false);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        unregisterReceiver(mBroadcastReceiver);
        if (mAudioManager != null && mAudioManager.getMode() != AudioManager.MODE_NORMAL) {
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            setVolumeControlStream(AudioManager.STREAM_SYSTEM); // 恢复音量控制
        }
    }

    private void initBroadcaseReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED:
                    case BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED:
                        Log.d(TAG, "action = " + action);
                        int state = intent.getIntExtra(BluetoothProfile.EXTRA_STATE, -1);
                        Log.d(TAG, "state = " + state);
                        switch (state) {
                            case BluetoothProfile.STATE_CONNECTED:
                                Log.d(TAG, "STATE_CONNECTED");
                                checkTheBluetoothConnect();
                                btnStartBluetooth.performClick(); // 蓝牙连接，打开sco
                                break;
                            case BluetoothProfile.STATE_DISCONNECTED:
                                Log.d(TAG, "STATE_DISCONNECTED");
                                checkTheBluetoothConnect();
                                btnStopBluetooth.performClick(); // 蓝牙断开，关闭sco
                                break;
                            default:
                                break;
                        }
                        break;
                    case AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED:
                        int scoAudioState = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1);
                        if (mAudioManager == null) {
                            return;
                        }
                        Log.d(TAG, "ACTION_SCO_AUDIO_STATE_UPDATED state = " + scoAudioState);
                        if (scoAudioState == AudioManager.SCO_AUDIO_STATE_CONNECTED) {
                            mAudioManager.setBluetoothScoOn(true);
                            checkTheBluetoothConnect();
                        } else if (scoAudioState == AudioManager.SCO_AUDIO_STATE_DISCONNECTED) {
                            mAudioManager.setBluetoothScoOn(false);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public boolean isBlueToothHeadsetConnected() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        // BluetoothProfile.HEADSET包括HSP和HFP, 双向传输, 用于打电话场景(耳机、手表)
        // BluetoothProfile.A2DP为高质量单向传输, 用于播放音乐(音响)
//        Log.d(TAG, "isBlueToothHeadsetConnected = " + adapter.getProfileConnectionState(BluetoothProfile.HEADSET));
        return adapter != null && adapter.isEnabled()
                && adapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothProfile.STATE_CONNECTED;
    }

    public void checkTheBluetoothConnect() {
        Log.d(TAG, "isBlueToothHeadsetConnected = " + isBlueToothHeadsetConnected());
//        Log.d(TAG, "isBluetoothScoOn = " + mAudioManager.isBluetoothScoOn());
//        Log.d(TAG,"isBluetoothA2dpOn = " + mAudioManager.isBluetoothA2dpOn());
    }

}