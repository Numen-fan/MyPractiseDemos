package com.jiajia.mypractisedemos.module.ndk;

/**
 * Created by Numen_fan on 2022/9/1
 * Desc:
 **/
public class NDKTools {

    static {
        System.loadLibrary("ndkdemotest-jni");
    }

    public static native String getStringFromNDK();

}


