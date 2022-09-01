//
// Created by Numen_fan on 2022/9/1.
//

#include "com_jiajia_mypractisedemos_module_ndk_NDKTools.h"

JNIEXPORT jstring JNICALL Java_com_jiajia_mypractisedemos_module_ndk_NDKTools_getStringFromNDK
  (JNIEnv *env, jclass obj) {
    return (*env) -> NewStringUTF(env, "我是JNI的Hello World啊");
  }