package com.jiajia.mypractisedemos.module.videocompressor;

import android.content.Context;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jiajia.mypractisedemos.R;

/**
 * Created by Numen_fan on 2024/4/8
 * Desc:
 */
public class VideoRecorderUtils {

    public static void initSpinner(Context context, Spinner encoder, Spinner resolution, Spinner bitrate) {
        // 1 初始化编码器算法
        ArrayAdapter<String> encoderAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item);
        encoderAdapter.add("VP8");
        encoderAdapter.add("H264");
        encoderAdapter.add("H265");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            encoderAdapter.add("VP9");
        }
        encoder.setAdapter(encoderAdapter);

        // 1 初始化编码器算法
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item);
//        sizeAdapter.add("320P");
        sizeAdapter.add("480P");
        sizeAdapter.add("720P");
        sizeAdapter.add("1080P");
        resolution.setAdapter(sizeAdapter);

        // 1 初始化编码器算法
        ArrayAdapter<String> bitAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item);
        bitAdapter.add("1024 x 1024");
        bitAdapter.add("720 x 480");
        bitAdapter.add("1280 x 720");
        bitAdapter.add("1920 x 1080");
        bitAdapter.add("2 x 1024 x 1024");
        bitAdapter.add("3 x 1024 x 1024");
        bitAdapter.add("5 x 1024 x 1024");
        bitrate.setAdapter(bitAdapter);
    }

    public static String getEncoderName(int encoder) {
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

    public static int getOutputFormat(String value) {
        switch (value) {
            case "VP8":
            case "VP9":
                return MediaRecorder.OutputFormat.WEBM;
            default:
                return MediaRecorder.OutputFormat.MPEG_4;
        }
    }

    public static String getOutputFormatFileSub(int encoder) {
        switch (encoder) {
            case MediaRecorder.VideoEncoder.VP8:
            case MediaRecorder.VideoEncoder.VP9:
                return ".webm";
            default:
                return ".mp4";
        }
    }

    public static int getBitRate(String value) {
        switch (value) {
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

    public static int getEncoderMethod(String value) {
        switch (value) {
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

    public static int getQuality(String value) {
        switch (value) {
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

    public static int getAudioEncoder(String value) {
        switch (value) {
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

}
