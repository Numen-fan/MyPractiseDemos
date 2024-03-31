package com.jiajia.mypractisedemos.module.videocompressor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.jiajia.mypractisedemos.databinding.ActivityVideoRecordBinding;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;
import com.vincent.videocompressor.VideoCompress;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoRecordActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "VideoRecordActivity";

    ActivityVideoRecordBinding binding;

    MediaRecorder mRecorder;

    SurfaceHolder surfaceHolder;
    Camera camera;

    private SurfaceView surfaceView;

    private Timer timer;

    String path = "/data/data/com.jiajia.mypractisedemos/cache/video/1711901573034_640_480.mp4";


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
        binding.btnCompress.setOnClickListener((v) -> {
            startCompress();
        });
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
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
//        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_VGA)) {
//            LogUtils.INSTANCE.error(TAG, "CamcorderProfile.QUALITY_VGA");
//        }
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P)) {
            LogUtils.INSTANCE.error(TAG, "CamcorderProfile.QUALITY_1080P");
        }
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
            LogUtils.INSTANCE.error(TAG, "CamcorderProfile.QUALITY_VGA");
        }
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P)) {
            LogUtils.INSTANCE.error(TAG, "CamcorderProfile.QUALITY_480P");
        }
        LogUtils.INSTANCE.error(TAG, "width = " + mProfile.videoFrameWidth + ", height = " + mProfile.videoFrameHeight);
        mRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
        mRecorder.setVideoFrameRate(30); //帧率
        mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024); //编码比特率
        mRecorder.setOrientationHint(90);
        // 设置记录会话的最大持续时间（毫秒）
        int duration = 3 * 60 * 1000;
        setSurfaceViewLayoutParams(mProfile.videoFrameHeight, mProfile.videoFrameWidth);
        mRecorder.setMaxDuration(duration);

        mRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        path = getCacheDir() + File.separator + "video";
        File dir = new File(path);
        if (!dir.exists()) {
            boolean res = dir.mkdir();
            LogUtils.INSTANCE.error(TAG, res + "");
        }
        path += File.separator + System.currentTimeMillis() + "_" + mProfile.videoFrameWidth + "_" + mProfile.videoFrameHeight + ".mp4";
        LogUtils.INSTANCE.error(TAG, path);
        mRecorder.setOutputFile(path);
        try {
            mRecorder.prepare();
            mRecorder.start();
            startTimer(duration);
        } catch (Exception e) {
            LogUtils.INSTANCE.error(TAG, e.getMessage());
        }
    }

    private void startCompress() {
        int index = path.lastIndexOf(".mp4");
        String dest = path.substring(0, index) + "_compress" + ".mp4";
        LogUtils.error(TAG, "dest = " + dest);
        VideoCompress.compressVideoLow(path, dest, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
                ToastUtils.showToast("开始压缩");
            }

            @Override
            public void onSuccess() {
                ToastUtils.showToast("压缩成功");
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onProgress(float percent) {

            }
        });
    }


    private void setSurfaceViewLayoutParams(int videoWidth, int videoHeight) {
        float videoProportion = (float) videoWidth / videoHeight;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        float screenProportion = (float) screenWidth / screenHeight;
        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        if (videoProportion > screenProportion) {
            lp.width = screenWidth;
            lp.height = (int) (screenWidth / videoProportion);
        } else {
            lp.width = (int) (videoProportion * screenHeight);
            lp.height = screenHeight;
        }
        surfaceView.setLayoutParams(lp);
    }

    private void startTimer(int duration) {
        if (timer == null) {
            timer = new Timer();
        }
        AtomicInteger time = new AtomicInteger();
        timer.schedule(new TimerTask() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (time.get() > duration / 1000) {
                        timer.cancel();
                    }
                    time.getAndIncrement();
                    binding.time.setText(time + "");
                });
            }
        }, 0, 1000);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        surfaceHolder = holder;
        camera.setDisplayOrientation(90);
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
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        surfaceView = null;
    }
}