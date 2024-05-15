package com.jiajia.mypractisedemos.module.videocompressor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.MediaStoreOutputOptions;
import androidx.camera.video.Quality;
import androidx.camera.video.QualitySelector;
import androidx.camera.video.Recorder;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.camera.video.VideoRecordEvent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;

import com.google.common.util.concurrent.ListenableFuture;
import com.jiajia.mypractisedemos.databinding.ActivityVideoRecordCameraXactivityBinding;
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class VideoRecordCameraXActivity extends AppCompatActivity {

    private static final String TAG = "VideoRecordCameraXActiv";

    ActivityVideoRecordCameraXactivityBinding binding;

    ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ProcessCameraProvider cameraProvider;

    Recorder recorder;

    Camera camera;

    VideoCapture videoCapture;

    Recording recording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoRecordCameraXactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {

            try {
                cameraProvider = cameraProviderFuture.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
//            bindPreview(cameraProvider);

            initRecorder();
            initVideoCapture();
        }, ContextCompat.getMainExecutor(this));


        binding.btnStart.setOnClickListener(v -> {
            startRecording();
        });

        binding.btnStop.setOnClickListener(v -> {
            stopRecording();
        });
    }


    @SuppressLint("UnsafeOptInUsageError")

    private void initRecorder() {
        //方法1
        List<Quality> list = new ArrayList<>();
        list.add(Quality.UHD);
        list.add(Quality.FHD);
        list.add(Quality.HD);
        list.add(Quality.SD);
        QualitySelector qualitySelector = QualitySelector.from(Quality.SD);

        //方法2
//        List<CameraInfo> cameraInfo = new ArrayList<>();
//        for (CameraInfo it : cameraProvider.getAvailableCameraInfos()) {
//            if (Camera2CameraInfo.from(it).getCameraCharacteristic(CameraCharacteristics.LENS_FACING)
//                    == CameraMetadata.LENS_FACING_BACK) {
//                cameraInfo.add(it);
//            }
//        }
//        List<Quality> supportedQualities = QualitySelector.getSupportedQualities(cameraInfo.get(0));
//        List<Quality> filteredQualities = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            filteredQualities = list.stream().filter(supportedQualities::contains).collect(Collectors.toList());
//        }
//        QualitySelector qualitySelector = QualitySelector.from(filteredQualities.get(0));  //position为选择项


        // 方法3
        recorder = new Recorder.Builder()
                .setExecutor(Executors.newFixedThreadPool(2))
                .setQualitySelector(qualitySelector)
                .build();
    }

    @SuppressLint("RestrictedApi")
    private void initVideoCapture() {
        videoCapture = VideoCapture.withOutput(recorder);
//        videoCapture.setVideoFrameRate(30); // 设置视频帧率
//        videoCapture.setBitRate(1024 * 1024); // 设置比特率
//        videoCapture.setTargetRotation(90); // 设置旋转角度
//        videoCapture.setAudioRecordSource(MediaRecorder.AudioSource.MIC);

//        videoCapture.setTargetRotation((int) binding.previewView.getRotation());
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
        try {
            camera = cameraProvider.bindToLifecycle(
                    this, CameraSelector.DEFAULT_BACK_CAMERA, preview, videoCapture);
        } catch (Exception exc) {
            Log.e(TAG, "Use case binding failed", exc);
        }
    }

    private void startRecording() {
        if (recording != null) {
            stopRecording();
        }
        String name = "CameraX-recording.mp4";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "Movies/CameraX-Video");
        }
        MediaStoreOutputOptions mediaStoreOutput = new MediaStoreOutputOptions.Builder(this.getContentResolver(),
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                .setContentValues(contentValues)
                .build();
        Recorder recorder = (Recorder) videoCapture.getOutput();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        recording = recorder.prepareRecording(this, mediaStoreOutput)
                .withAudioEnabled()
                .start(ContextCompat.getMainExecutor(this), videoRecordEvent -> {
                    if (videoRecordEvent instanceof VideoRecordEvent.Start) {
                        ToastUtils.showToast("开始录制了");
                    } else if (videoRecordEvent instanceof VideoRecordEvent.Finalize) {
                        if (((VideoRecordEvent.Finalize) videoRecordEvent).hasError()) {
                            ToastUtils.showToast("出错了");
                            stopRecording();
                        } else {
                            String msg = "视频为" + ((VideoRecordEvent.Finalize) videoRecordEvent).getOutputResults().getOutputUri();
                            Log.i("CameraXTest", msg);
                        }
                    }
                });
    }

    private void stopRecording() {
        if (recording != null) {
            recording.stop();
            recording.close();
            recording = null;
        }
    }

//    void bindPreview(ProcessCameraProvider cameraProvider) {
//        Preview preview = new Preview.Builder().build();
//        CameraSelector cameraSelector = new CameraSelector.Builder()
//                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                .build();
//        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
//        // 拿到camera对象
//        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview);
//
//    }
}