package com.jiajia.mypractisedemos.module.videocompressor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.jiajia.mypractisedemos.databinding.ActivityVideoRecordBinding;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;
import com.vincent.videocompressor.VideoCompress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import io.microshow.rxffmpeg.RxFFmpegCommandList;
import io.microshow.rxffmpeg.RxFFmpegInvoke;

public class VideoRecordActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "VideoRecordActivity";

    ActivityVideoRecordBinding binding;

    MediaRecorder mRecorder;

    SurfaceHolder surfaceHolder;
    Camera camera;

    private SurfaceView surfaceView;

    private Timer timer;

    private int fps = 0;

    // /data/user/0/com.jiajia.mypractisedemos/cache
    String path = "/data/data/com.jiajia.mypractisedemos/cache/video/1712066565346.mp4";
    long compressDuration = 0;
//    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        surfaceView = binding.surface;
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        binding.btnStart.setOnClickListener((v) -> {
            startRecord();
        });
        surfaceView.getHolder().addCallback(this);
        binding.btnCompress.setOnClickListener((v) -> {
            startCompress();
//            RxFFmpegComperssor.startRxFFmpegCompress(path);
//            transBase64();
//            showVideoInfo();s
//            showCompressVideoInfo();
        });
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_2K)) {
            LogUtils.error(TAG, "CamcorderProfile.QUALITY_2K");
        } else {
            LogUtils.error(TAG, "no CamcorderProfile.QUALITY_2K");
        }
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P)) {
            LogUtils.error(TAG, "CamcorderProfile.QUALITY_1080P");
        }
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
            LogUtils.error(TAG, "CamcorderProfile.QUALITY_720P");
        }
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P)) {
            LogUtils.error(TAG, "CamcorderProfile.QUALITY_480P");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50) {
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
        int encoder = MediaRecorder.VideoEncoder.HEVC;
        mRecorder.setVideoEncoder(encoder);
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);

        LogUtils.error(TAG, "width = " + mProfile.videoFrameWidth + ", height = " + mProfile.videoFrameHeight + ",videoBitRate = " + mProfile.videoBitRate);
        mRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
        int bitRate = Math.min(mProfile.audioBitRate, mProfile.videoFrameHeight * mProfile.videoFrameWidth);
        LogUtils.error(TAG, "FrameRate = " + mProfile.videoFrameRate + ", min fps = " + fps + ", bitRate = " + bitRate);
        mRecorder.setVideoFrameRate(mProfile.videoFrameRate); // 帧率
        mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024); //编码比特率
        mRecorder.setOrientationHint(90);
        // 设置记录会话的最大持续时间（毫秒）
        int duration = 1 * 30 * 1000;
        setSurfaceViewLayoutParams(mProfile.videoFrameHeight, mProfile.videoFrameWidth);
        mRecorder.setMaxDuration(duration);
        mRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        path = getExternalCacheDir() + File.separator + "video";
        File dir = new File(path);
        if (!dir.exists()) {
            boolean res = dir.mkdir();
            LogUtils.error(TAG, res + "");
        }
        path += File.separator + System.currentTimeMillis() + "_" + mProfile.videoFrameWidth + "_" +
                mProfile.videoFrameHeight + "_" + getEncoderName(encoder) + ".mp4";
        LogUtils.error(TAG, path);
        mRecorder.setOutputFile(path);
        try {
            mRecorder.prepare();
            mRecorder.start();
            startTimer(duration);
        } catch (Exception e) {
            LogUtils.error(TAG, e.getMessage());
            ToastUtils.showToast("录制失败");
        }
    }

    private void startCompress() {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.showToast("媒体文件不存在");
            return;
        }
        int index = path.lastIndexOf(".mp4");
        String dest = path.substring(0, index) + "_compress" + ".mp4";
        LogUtils.error(TAG, "dest = " + dest);
        long startTime = System.currentTimeMillis();
        VideoCompress.compressVideoLow(path, dest, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
                ToastUtils.showToast("开始压缩");
            }

            @Override
            public void onSuccess() {
                ToastUtils.showToast("压缩成功");
                compressDuration = (System.currentTimeMillis() - startTime) / 1000;
                showCompressVideoInfo();
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onProgress(float percent) {
                binding.process.setText(String.format(Locale.getDefault(), "%.2f", percent) + "%");
            }
        });
    }

    private void showCompressVideoInfo() {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.showToast("媒体文件不存在");
            return;
        }
        int index = path.lastIndexOf(".mp4");
        String dest = path.substring(0, index) + "_compress" + ".mp4";
        LogUtils.error(TAG, "dest = " + dest);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(dest);
        String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        int bitRate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
        String rate_s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
//        if (TextUtils.isEmpty(rate_s)) {
//            String count_s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
//            long count = Long.parseLong(count_s);
//            //计算帧率
//            long dt = duration / count; // 平均每帧的时间间隔，35ms
//            rate_s = String.valueOf(((int) count / duration)); // 帧率
//        }

        binding.compressInfoSize.setText("大小：" + String.format("%.2f", new File(dest).length() / 1024f / 1024f) + "M");
        binding.compressInfoVideoSize.setText("分辨率：" + width + "x" + height);
        binding.compressInfoFrame.setText("帧率:" + rate_s);
        binding.compressInfoDuration.setText("时长：" + duration + "s");
        binding.compressInfoTime.setText("耗时：" + compressDuration + "s");
    }

    private void transBase64() {
        String base64 = null;
        InputStream in = null;
        File file = new File(path);
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (base64 == null) {
            return;
        }
        LogUtils.error(TAG, "origin size = " + file.length() / 1024 / 1024 + ", base64 size = " + base64.getBytes().length / 1024 / 1024);
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
                        mRecorder.stop();
                        camera.stopPreview();
                        showVideoInfo();
                    }
                    time.getAndIncrement();
                    binding.time.setText(time + "");
                });
            }
        }, 0, 1000);
    }

    private void showVideoInfo() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        int bitRate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
        String rate_s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
        if (TextUtils.isEmpty(rate_s)) {
            String count_s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
            long count = Long.valueOf(count_s);
            //计算帧率
            long dt = duration / count; // 平均每帧的时间间隔，35ms
            rate_s = String.valueOf(((int) count / duration)); // 帧率
        }

        binding.infoSize.setText("大小：" + String.format("%.2f", new File(path).length() / 1024f / 1024f) + "M");
        binding.infoVideoSize.setText("分辨率：" + width + "x" + height);
        binding.infoFrame.setText("帧率:" + rate_s);
        binding.infoDuration.setText("时长：" + duration + "s");
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        surfaceHolder = holder;
        camera.setDisplayOrientation(90);
        camera.startPreview();
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (canAutoFocus(camera)) {
            camera.autoFocus(null);
        } else {
            LogUtils.error(TAG, "不支持自动聚焦");
        }
        fps = getMinFps(camera);
        camera.unlock();
    }

    private String getEncoderName(int encoder) {
        switch (encoder) {
            case MediaRecorder.VideoEncoder.H264:
                return "H264";
            case MediaRecorder.VideoEncoder.HEVC:
                return "H265";
            case MediaRecorder.VideoEncoder.MPEG_4_SP:
                return "MPEG_4_SP";
            case MediaRecorder.VideoEncoder.VP8:
                return "VP8";
            case MediaRecorder.VideoEncoder.VP9:
                return "VP9";
            default:
                return "unknown";
        }
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

    private boolean canAutoFocus(Camera camera) {
        if (camera == null) {
            return false;
        }
        Camera.Parameters params = camera.getParameters();
        if (params == null) {
            return false;
        }
        String focusMode = params.getFocusMode();
        return focusMode != null && focusMode.contains(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    private int getMinFps(Camera camera) {
        List<int[]> fdps = camera.getParameters().getSupportedPreviewFpsRange();
        int res = Integer.MAX_VALUE;
        for (int[] fps : fdps) {
            res = Math.min(fps[0], res);
        }
        return res / 1000;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxFFmpegInvoke.getInstance().exit();
    }
}