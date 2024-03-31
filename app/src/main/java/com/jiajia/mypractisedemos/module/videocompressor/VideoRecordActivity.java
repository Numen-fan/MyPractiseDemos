package com.jiajia.mypractisedemos.module.videocompressor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jiajia.mypractisedemos.databinding.ActivityVideoRecordBinding;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;

import java.io.File;

public class VideoRecordActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "VideoRecordActivity";

    ActivityVideoRecordBinding binding;

    MediaRecorder mRecorder;

    SurfaceHolder surfaceHolder;
    Camera camera;

    private SurfaceView surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        surfaceView = binding.surface;
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        binding.btnStart.setOnClickListener((v) -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        50);
            } else {
                startRecord();
            }
        });
        surfaceView.getHolder().addCallback(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecord();
        }
    }

    private void startRecord() {
        mRecorder = new MediaRecorder();
        mRecorder.reset();
        mRecorder.setCamera(camera);
        // 视频音频源
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // 输出文件格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        // 编码器 注意，如果使用AMR_NB将会导致IOS无法播放
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.HEVC);
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_VGA);
        }
        LogUtils.INSTANCE.error(TAG, "width = " + mProfile.videoFrameWidth + ", height = " + mProfile.videoFrameHeight);
//        mRecorder.setVideoSize(1080, 720); //输出视频的分辨率
//        mRecorder.setVideoSize(640, 480);
        mRecorder.setVideoFrameRate(30); //帧率
        mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024); //编码比特率
        mRecorder.setOrientationHint(90);
        //设置记录会话的最大持续时间（毫秒）
        mRecorder.setMaxDuration(30 * 1000);
        mRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        String path = getCacheDir() + File.separator + "video";
        File dir = new File(path);
        if (!dir.exists()) {
            boolean res = dir.mkdir();
            LogUtils.INSTANCE.error(TAG, res + "");
        }
        path += File.separator + System.currentTimeMillis() + ".mp4";
        LogUtils.INSTANCE.error(TAG, path);
        mRecorder.setOutputFile(path);
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (Exception e) {
            LogUtils.INSTANCE.error(TAG, e.getMessage());
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        surfaceHolder = holder;
        camera.startPreview();
        camera.unlock();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
//        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        surfaceHolder = null;
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }
        surfaceView = null;
    }
}