package com.jiajia.mypractisedemos.module.videocompressor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.jiajia.mypractisedemos.R;
import com.jiajia.mypractisedemos.databinding.ActivityVideoRecordBinding;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;
import com.jiajia.mypractisedemos.utils.FileUtils;
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
    String path = "/storage/sdcard0/DCIM/video/1712118290980_1920_1080_H265.mp4";
    long compressDuration = 0;

    private String BASE_URL = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath() + File.separator + "video";
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
        binding.btnStop.setOnClickListener((v)-> {
            stopRecord();
        });
//        checkSupportedVideoCodecs();
        surfaceView.getHolder().addCallback(this);
        binding.btnCompress.setOnClickListener((v) -> {
            startCompress();
//            RxFFmpegComperssor.startRxFFmpegCompress(path);
//            transBase64();
//            showVideoInfo();s
//            showCompressVideoInfo();
//            startLightCompressor();
//            try {
//                FileUtils.ZipFolder(path, getCacheDir() + "/video" + "/aaa.zip");
//            } catch (Exception e) {
//                ToastUtils.showToast("zip失败");
//                LogUtils.error(TAG, e.getMessage());
//            }
        });
        initSpinner();
        File dir = new File(BASE_URL);
        if (!dir.exists()) {
            boolean res = dir.mkdir();
            LogUtils.error(TAG, res + "");
        }

    }

    private void initSpinner() {
        // 1 初始化编码器算法
        ArrayAdapter<String> encoderAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        encoderAdapter.add("H264");
        encoderAdapter.add("H265");
        encoderAdapter.add("VP8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            encoderAdapter.add("VP9");
        }
        binding.encoderMethod.setAdapter(encoderAdapter);

        // 1 初始化编码器算法
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        sizeAdapter.add("320P");
        sizeAdapter.add("480P");
        sizeAdapter.add("720P");
        sizeAdapter.add("1080P");
        binding.videoSize.setAdapter(sizeAdapter);

        // 1 初始化编码器算法
        ArrayAdapter<String> bitAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        bitAdapter.add("1024 x 1024");
        bitAdapter.add("720 x 480");
        bitAdapter.add("1280 x 720");
        bitAdapter.add("1920 x 1080");
        bitAdapter.add("2 x 1024 x 1024");
        bitAdapter.add("3 x 1024 x 1024");
        bitAdapter.add("5 x 1024 x 1024");
        binding.videoBitRate.setAdapter(bitAdapter);
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
        mRecorder.setOutputFormat(getOutputFormat());
        // 编码器 注意，如果使用AMR_NB将会导致IOS无法播放
        mRecorder.setAudioEncoder(getAudioEncoder());
        int encoder = getEncoderMethod();
        mRecorder.setVideoEncoder(encoder);
        //设置比特率（比特率越高质量越高同样也越大）
//        mRecorder.setAudioEncodingBitRate(1280);
//        mRecorder.setAudioSamplingRate(44100);

        CamcorderProfile mProfile = CamcorderProfile.get(getQuality());

        LogUtils.error(TAG, "width = " + mProfile.videoFrameWidth + ", height = " + mProfile.videoFrameHeight + ",videoBitRate = " + mProfile.videoBitRate);
        mRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
        int bitRate = mProfile.videoBitRate;
        LogUtils.error(TAG, "FrameRate = " + mProfile.videoFrameRate + ", min fps = " + fps + ", bitRate = " + bitRate);
        mRecorder.setVideoFrameRate(mProfile.videoFrameRate); // 帧率
        mRecorder.setVideoEncodingBitRate(getBitRate()); //编码比特率
//        mRecorder.setVideoEncodingBitRate(Math.min(bitRate, 8 * 1920 * 1080));
        mRecorder.setOrientationHint(270);
        // 设置记录会话的最大持续时间（毫秒）
        int duration = TextUtils.isEmpty(binding.recordTime.getText().toString()) ? 30 * 1000 : Integer.parseInt(binding.recordTime.getText().toString()) * 1000;
        setSurfaceViewLayoutParams(mProfile.videoFrameHeight, mProfile.videoFrameWidth);
        mRecorder.setMaxDuration(duration);
        mRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        path = BASE_URL + File.separator + System.currentTimeMillis() + "_" + mProfile.videoFrameWidth + "_" +
                mProfile.videoFrameHeight + "_" + getEncoderName(encoder) + getOutputFormatFileSub();
        LogUtils.error(TAG, path);
        mRecorder.setOutputFile(path);
        mRecorder.setOnInfoListener((mr, what, extra) -> {
            LogUtils.error(TAG, "" + what);
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                stopRecord();
            }
        });
        try {
            mRecorder.prepare();
            mRecorder.start();
            startTimer(duration);
        } catch (Exception e) {
            LogUtils.error(TAG, e.getMessage());
            ToastUtils.showToast("录制失败");
        }
    }

    private void stopRecord() {
        if (timer == null || mRecorder == null) {
            return;
        }
        timer.cancel();
        timer = null;
        mRecorder.stop();
        mRecorder.release();
        try {
            showVideoInfo();
        } catch (Exception e) {
            ToastUtils.showToast("提前视频信息失败，请前往相册查看");
        }
        FileUtils.saveVideo(VideoRecordActivity.this, new File(path));
    }

    private void startCompress() {
        int index = path.lastIndexOf(".mp4");
        path = path.substring(0, index) + " (1).mp4";
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.showToast("媒体文件不存在");
            return;
        }
        index = path.lastIndexOf(".mp4");
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
                FileUtils.saveVideo(VideoRecordActivity.this, new File(dest));
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
        AtomicInteger time = new AtomicInteger(1);
        timer.schedule(new TimerTask() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                runOnUiThread(() -> {
                    binding.time.setText(time + "");
                    if (time.get() > duration / 1000 && path.endsWith("webm")) {
                        stopRecord();
                        return;
                    }
                    time.getAndIncrement();
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
            if (!TextUtils.isEmpty(count_s)) {
                long count = Long.valueOf(count_s);
                //计算帧率
                rate_s = String.valueOf(((int) count / duration)); // 帧率
            }
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

    private int getBitRate() {
        switch ((String) binding.videoBitRate.getSelectedItem()) {
            case "720 x 480":
                return 720 * 480;
            case "512 x 512":
                return 512 * 512;
            case "1024 x 1024":
                return 1024 * 1024;
            case "2 x 1024 x 1024":
                return 2 * 1024 * 1024;
            case "3 x 1024 x 1024":
                return 3 * 1024 * 1024;
            case "4 x 1024 x 1024":
                return 4 * 1024 * 1024;
            case "5 x 1024 x 1024":
                return 5 * 1024 * 1024;
            case "1280 x 720":
                return 1280 * 720;
            case "1920 x 1080":
                return 1920 * 1080;
            default:
                return 1024 * 1024;
        }
    }

    private int getEncoderMethod() {
        switch ((String) binding.encoderMethod.getSelectedItem()) {
            case "H264":
                return MediaRecorder.VideoEncoder.H264;
            case "H265":
                return MediaRecorder.VideoEncoder.HEVC;
            case "VP8":
                return MediaRecorder.VideoEncoder.VP8;
            case "VP9":
                return MediaRecorder.VideoEncoder.VP9;
            default:
                return MediaRecorder.VideoEncoder.DEFAULT;
        }
    }

    private int getQuality() {
        switch ((String) binding.videoSize.getSelectedItem()) {
            case "320P":
                return CamcorderProfile.QUALITY_CIF;
            case "480P":
                return CamcorderProfile.QUALITY_480P;
            case "720P":
                return CamcorderProfile.QUALITY_720P;
            case "1080P":
                return CamcorderProfile.QUALITY_1080P;
            default:
                return CamcorderProfile.QUALITY_HIGH;
        }
    }

    private int getOutputFormat() {
        switch ((String) binding.encoderMethod.getSelectedItem()) {
            case "VP8":
            case "VP9":
                return MediaRecorder.OutputFormat.WEBM;
            default:
                return MediaRecorder.OutputFormat.MPEG_4;
        }
    }

    private int getAudioEncoder() {
        switch ((String) binding.encoderMethod.getSelectedItem()) {
            case "VP8":
            case "VP9":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    return MediaRecorder.AudioEncoder.OPUS;
                } else {
                    return MediaRecorder.AudioEncoder.AAC;
                }
            default:
                return MediaRecorder.AudioEncoder.AAC;
        }
    }

    private String getOutputFormatFileSub() {
        switch ((String) binding.encoderMethod.getSelectedItem()) {
            case "VP8":
            case "VP9":
                return ".webm";
            default:
                return ".mp4";
        }
    }

    public static void checkSupportedVideoCodecs() {
        MediaCodecList codecList = new MediaCodecList(MediaCodecList.REGULAR_CODECS);
        MediaCodecInfo[] codecs = codecList.getCodecInfos();
        for (MediaCodecInfo codec : codecs) {
            if (codec.isEncoder()) {
                LogUtils.error(TAG, "Codec Name: " + codec.getName());
                String[] supportedTypes = codec.getSupportedTypes();
                for (String type : supportedTypes) {
                    LogUtils.error(TAG, "Supported Type: " + type);
                }
            }
        }
    }
}