package com.jiajia.mypractisedemos.module.videocompressor;

import android.media.MediaRecorder;

/**
 * Created by Numen_fan on 2024/4/8
 * Desc:
 */
public class VideoRecorderUtils {

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

    public static String getOutputFormatFileSub(int encoder) {
        switch (encoder) {
            case MediaRecorder.VideoEncoder.VP8:
            case MediaRecorder.VideoEncoder.VP9:
                return ".webm";
            default:
                return ".mp4";
        }
    }

}
