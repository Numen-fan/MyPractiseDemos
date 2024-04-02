package com.jiajia.mypractisedemos.module.videocompressor;

import io.microshow.rxffmpeg.RxFFmpegCommandList;
import io.microshow.rxffmpeg.RxFFmpegInvoke;

/**
 * Created by Numen_fan on 2024/4/2
 * Desc:
 */
public class RxFFmpegComperssor {

    public static void startRxFFmpegCompress(String path) {
        String command = "ffmpeg -y -i "+ path + " -vf /data/data/com.jiajia.mypractisedemos/cache/video/171206202838912345.mp4";
        String[] commands = command.split(" ");
        MyRxFFmpegSubscriber myRxFFmpegSubscriber = new MyRxFFmpegSubscriber();
        //开始执行FFmpeg命令
        RxFFmpegInvoke.getInstance()
                .runCommandRxJava(commands)
                .subscribe(myRxFFmpegSubscriber);
        RxFFmpegInvoke.getInstance().runCommand(commands, null);
    }

    public static String[] getBoxblur(String path) {
//        ffmpeg -i Desktop/吉他.mp4  -r 20  Desktop/output1.mp4
        int index = path.lastIndexOf(".mp4");
        String dest = path.substring(0, index) + "123" + ".mp4";
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(path);
//        cmdlist.append("-vf");
//        cmdlist.append("boxblur=25:5");
//        cmdlist.append("-preset");
//        cmdlist.append("superfast");
        cmdlist.append("-crf");
        cmdlist.append("30");
//        cmdlist.append("20");
        cmdlist.append(dest);
        return cmdlist.build();
    }

}
