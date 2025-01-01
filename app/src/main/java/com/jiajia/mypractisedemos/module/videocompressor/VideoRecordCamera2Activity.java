package com.jiajia.mypractisedemos.module.videocompressor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaMuxer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import com.jiajia.mypractisedemos.databinding.ActivityVideoRecordCamera2Binding;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;
import com.jiajia.mypractisedemos.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoRecordCamera2Activity extends AppCompatActivity {

    private static final String TAG = "VideoRecordCamera2Activity";

    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraDevice.StateCallback mCameraDeviceStateCallback;
    private CameraCaptureSession.StateCallback mSessionStateCallback;
    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback;
    private CaptureRequest.Builder mPreviewCaptureRequest;
    private CaptureRequest.Builder mRecorderCaptureRequest;
    private MediaRecorder mMediaRecorder;
    private String mCurrentSelectCamera;
    private Handler mChildHandler;

    private Timer timer;

    private String path;

    int duration;

    ActivityVideoRecordCamera2Binding binding;

    private final String BASE_URL = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath() + File.separator + "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoRecordCamera2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        VideoRecorderUtils.initSpinner(this, binding.encoderMethod, binding.videoSize, binding.videoBitRate);
        initTextureViewStateListener();
        initClickListener();
        initChildHandler();
        initMediaRecorder();
        initCameraDeviceStateCallback();
        initSessionStateCallback();
        initSessionCaptureCallback();
        File dir = new File(BASE_URL);
        if (!dir.exists()) {
            boolean res = dir.mkdir();
            LogUtils.error(TAG, res + "");
        }
    }

    private void initClickListener() {
        binding.btnStart.setOnClickListener((v) -> {
            config();
            startRecorder();
            startTimer(duration);
        });
        binding.btnStop.setOnClickListener((v) -> {
            stopRecorder();
        });
    }

    /**
     * 初始化TextureView的纹理生成监听，只有纹理生成准备好了。我们才能去进行摄像头的初始化工作让TextureView接收摄像头预览画面
     */
    private void initTextureViewStateListener() {
        binding.textureview.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                //可以使用纹理
                initCameraManager();
                selectCamera();
                openCamera();

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                //纹理尺寸变化

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                //纹理被销毁
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                //纹理更新

            }
        });
    }

    /**
     * 初始化子线程Handler，操作Camera2需要一个子线程的Handler
     */
    private void initChildHandler() {
        HandlerThread handlerThread = new HandlerThread("Camera2Demo");
        handlerThread.start();
        mChildHandler = new Handler(handlerThread.getLooper());
    }

    /**
     * 初始化MediaRecorder
     */
    private void initMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
    }

    /**
     * 配置录制视频相关数据
     */
    private void configMediaRecorder() {
        String encodeMethod = (String) binding.encoderMethod.getSelectedItem();
        String resolution = (String) binding.videoSize.getSelectedItem();
        String bitrate = (String) binding.videoBitRate.getSelectedItem();
        CamcorderProfile mProfile = CamcorderProfile.get(VideoRecorderUtils.getQuality(resolution));
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置音频来源
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);// 设置视频来源
        mMediaRecorder.setOutputFormat(VideoRecorderUtils.getOutputFormat(encodeMethod));// 设置输出格式
        mMediaRecorder.setAudioEncoder(VideoRecorderUtils.getAudioEncoder(encodeMethod));// 设置音频编码格式，请注意这里使用默认，实际app项目需要考虑兼容问题，应该选择AAC
        int encoder = VideoRecorderUtils.getEncoderMethod(encodeMethod);
        mMediaRecorder.setVideoEncoder(encoder); // 设置视频编码格式，请注意这里使用默认，实际app项目需要考虑兼容问题，应该选择H264
        mMediaRecorder.setVideoEncodingBitRate(VideoRecorderUtils.getBitRate(bitrate));// 设置比特率 一般是 1*分辨率 到 10*分辨率 之间波动。比特率越大视频越清晰但是视频文件也越大。
        mMediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);// 设置帧数 选择 30即可， 过大帧数也会让视频文件更大当然也会更流畅，但是没有多少实际提升。人眼极限也就30帧了。
        duration = TextUtils.isEmpty(binding.recordTime.getText().toString()) ? 30 * 1000 : Integer.parseInt(binding.recordTime.getText().toString()) * 1000;
        mMediaRecorder.setMaxDuration(duration);
        mMediaRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
        mMediaRecorder.setOrientationHint(90);
        Surface surface = new Surface(binding.textureview.getSurfaceTexture());
        mMediaRecorder.setPreviewDisplay(surface);
        path = BASE_URL + File.separator + System.currentTimeMillis() + "_" + mProfile.videoFrameWidth + "_" +
                mProfile.videoFrameHeight + "_" + VideoRecorderUtils.getEncoderName(encoder) + VideoRecorderUtils.getOutputFormatFileSub(encoder);
        LogUtils.error(TAG, path);
        mMediaRecorder.setOutputFile(path);
        mMediaRecorder.setOnInfoListener((mr, what, extra) -> {
            if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                ToastUtils.showToast("录制完成");
                stopRecorder();
            }
        });
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新配置录制视频时的CameraCaptureSession
     */
    private void config() {
        try {
            mCameraCaptureSession.stopRepeating();//停止预览，准备切换到录制视频
            mCameraCaptureSession.close();//关闭预览的会话，需要重新创建录制视频的会话
            mCameraCaptureSession = null;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        configMediaRecorder();
        Size cameraSize = getMatchingSize2();
        SurfaceTexture surfaceTexture = binding.textureview.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(cameraSize.getWidth(), cameraSize.getHeight());
        Surface previewSurface = new Surface(surfaceTexture);
        Surface recorderSurface = mMediaRecorder.getSurface();// 从获取录制视频需要的Surface
        try {
            mPreviewCaptureRequest = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            mPreviewCaptureRequest.addTarget(previewSurface);
            mPreviewCaptureRequest.addTarget(recorderSurface);
            //请注意这里设置了Arrays.asList(previewSurface,recorderSurface) 2个Surface，很好理解录制视频也需要有画面预览，第一个是预览的Surface，第二个是录制视频使用的Surface
            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface, recorderSurface), mSessionStateCallback, mChildHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 开始录制视频
     */
    private void startRecorder() {
        mMediaRecorder.start();
    }

    /**
     * 暂停录制视频（暂停后视频文件会自动保存）
     */
    private void stopRecorder() {
        if (timer == null) {
            return;
        }
        timer.cancel();
        timer = null;
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        try {
            showVideoInfo();
        } catch (Exception e) {
            LogUtils.error(TAG, e.getMessage());
        }
        FileUtils.saveVideo(this, new File(path));
    }

    /**
     * 初始化Camera2的相机管理，CameraManager用于获取摄像头分辨率，摄像头方向，摄像头id与打开摄像头的工作
     */
    private void initCameraManager() {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
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
                        stopRecorder();
                        return;
                    }
                    time.getAndIncrement();
                });
            }
        }, 0, 1000);
    }

    /**
     * 选择一颗我们需要使用的摄像头，主要是选择使用前摄还是后摄或者是外接摄像头
     */
    private void selectCamera() {
        if (mCameraManager == null) {
            LogUtils.error(TAG, "selectCamera: CameraManager is null");
            throw new RuntimeException("mCameraManager is null");
        }
        try {
            String[] cameraIdList = mCameraManager.getCameraIdList();   //获取当前设备的全部摄像头id集合
            if (cameraIdList.length == 0) {
                LogUtils.error(TAG, "selectCamera: cameraIdList length is 0");
            }
            for (String cameraId : cameraIdList) { //遍历所有摄像头
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(cameraId);//得到当前id的摄像头描述特征
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING); //获取摄像头的方向特征信息
                if (facing == CameraCharacteristics.LENS_FACING_BACK) { //这里选择了后摄像头
                    mCurrentSelectCamera = cameraId;

                }
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void initCameraDeviceStateCallback() {
        mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                //摄像头被打开
                try {
                    mCameraDevice = camera;
                    Size cameraSize = getMatchingSize2();//计算获取需要的摄像头分辨率
                    SurfaceTexture surfaceTexture = binding.textureview.getSurfaceTexture();//得到纹理
                    surfaceTexture.setDefaultBufferSize(cameraSize.getWidth(), cameraSize.getHeight());
                    Surface previewSurface = new Surface(surfaceTexture);
                    mPreviewCaptureRequest = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    mPreviewCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    mPreviewCaptureRequest.addTarget(previewSurface);
                    mCameraDevice.createCaptureSession(Collections.singletonList(previewSurface), mSessionStateCallback, mChildHandler);//创建数据捕获会话，用于摄像头画面预览，这里需要等待mSessionStateCallback回调
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {
                //摄像头断开

            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {
                //异常

            }
        };
    }

    private void initSessionStateCallback() {
        mSessionStateCallback = new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session) {
                mCameraCaptureSession = session;
                try {
                    //执行重复获取数据请求，等于一直获取数据呈现预览画面，mSessionCaptureCallback会返回此次操作的信息回调
                    mCameraCaptureSession.setRepeatingRequest(mPreviewCaptureRequest.build(), mSessionCaptureCallback, mChildHandler);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {

            }
        };
    }

    private void initSessionCaptureCallback() {
        mSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
            @Override
            public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                super.onCaptureStarted(session, request, timestamp, frameNumber);
            }

            @Override
            public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
                super.onCaptureProgressed(session, request, partialResult);
            }

            @Override
            public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                super.onCaptureCompleted(session, request, result);
            }

            @Override
            public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
                super.onCaptureFailed(session, request, failure);
            }
        };
    }

    /**
     * 打开摄像头，这里打开摄像头后，我们需要等待mCameraDeviceStateCallback的回调
     */
    @SuppressLint("MissingPermission")
    private void openCamera() {
        try {
            mCameraManager.openCamera(mCurrentSelectCamera, mCameraDeviceStateCallback, mChildHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算需要的使用的摄像头分辨率
     *
     * @return
     */
    private Size getMatchingSize2() {
        Size selectSize = null;
        try {
            CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(mCurrentSelectCamera);
            StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size[] sizes = streamConfigurationMap.getOutputSizes(ImageFormat.JPEG);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics(); //因为我这里是将预览铺满屏幕,所以直接获取屏幕分辨率
            int deviceWidth = displayMetrics.widthPixels; //屏幕分辨率宽
            int deviceHeigh = displayMetrics.heightPixels; //屏幕分辨率高
            LogUtils.error(TAG, "getMatchingSize2: 屏幕密度宽度=" + deviceWidth);
            LogUtils.error(TAG, "getMatchingSize2: 屏幕密度高度=" + deviceHeigh);
            /**
             * 循环40次,让宽度范围从最小逐步增加,找到最符合屏幕宽度的分辨率,
             * 你要是不放心那就增加循环,肯定会找到一个分辨率,不会出现此方法返回一个null的Size的情况
             * ,但是循环越大后获取的分辨率就越不匹配
             */
            for (int j = 1; j < 41; j++) {
                for (int i = 0; i < sizes.length; i++) { //遍历所有Size
                    Size itemSize = sizes[i];
//                    LogUtils.error(TAG, "当前itemSize 宽=" + itemSize.getWidth() + "高=" + itemSize.getHeight());
                    //判断当前Size高度小于屏幕宽度+j*5  &&  判断当前Size高度大于屏幕宽度-j*5  &&  判断当前Size宽度小于当前屏幕高度
                    if (itemSize.getHeight() < (deviceWidth + j * 5) && itemSize.getHeight() > (deviceWidth - j * 5)) {
                        if (selectSize != null) { //如果之前已经找到一个匹配的宽度
                            if (Math.abs(deviceHeigh - itemSize.getWidth()) < Math.abs(deviceHeigh - selectSize.getWidth())) { //求绝对值算出最接近设备高度的尺寸
                                selectSize = itemSize;
                                continue;
                            }
                        } else {
                            selectSize = itemSize;
                        }

                    }
                }
                if (selectSize != null) { //如果不等于null 说明已经找到了 跳出循环
                    break;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        LogUtils.error(TAG, "getMatchingSize2: 选择的分辨率宽度=" + selectSize.getWidth());
        LogUtils.error(TAG, "getMatchingSize2: 选择的分辨率高度=" + selectSize.getHeight());
        return selectSize;
    }

    private void showVideoInfo() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        int bitRate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
        String rate_s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
        String rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
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
        binding.infoDuration.setText("时长：" + duration + "s" + ", rotation=" + rotation);

        try {
            setRotation(path, BASE_URL + File.separator + System.currentTimeMillis() + ".webm", 90);
        } catch (IOException e) {
            LogUtils.error(TAG, e.getMessage());
        }
    }

    public static void setRotation(String inputPath, String outputPath, int rotationDegrees) throws IOException {
        MediaExtractor extractor = new MediaExtractor();
        extractor.setDataSource(inputPath);

        MediaMuxer muxer = new MediaMuxer(outputPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_WEBM);

        int videoTrackIndex = -1;
        int numTracks = extractor.getTrackCount();

        for (int i = 0; i < numTracks; i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);

            if (mime.startsWith("video/")) {
                videoTrackIndex = muxer.addTrack(format);
                muxer.setOrientationHint(rotationDegrees);
            } else {
                muxer.addTrack(format);
            }
        }

        muxer.start();

        if (videoTrackIndex != -1) {
            extractor.selectTrack(videoTrackIndex);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);

            while (true) {
                int sampleSize = extractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    break;
                }
                bufferInfo.offset = 0;
                bufferInfo.size = sampleSize;
                bufferInfo.flags = extractor.getSampleFlags();
                bufferInfo.presentationTimeUs = extractor.getSampleTime();
                muxer.writeSampleData(videoTrackIndex, buffer, bufferInfo);
                extractor.advance();
            }
        }

        muxer.stop();
        muxer.release();
        extractor.release();
    }
}