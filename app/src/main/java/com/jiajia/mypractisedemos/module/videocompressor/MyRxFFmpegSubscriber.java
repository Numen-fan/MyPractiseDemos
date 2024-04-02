package com.jiajia.mypractisedemos.module.videocompressor;

import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils;

import io.microshow.rxffmpeg.RxFFmpegSubscriber;

/**
 * Created by Numen_fan on 2024/4/2
 * Desc:
 */
public class MyRxFFmpegSubscriber extends RxFFmpegSubscriber {

//    private WeakReference<HomeFragment> mWeakReference;

    public MyRxFFmpegSubscriber() {
    }

    @Override
    public void onFinish() {
//        final HomeFragment mHomeFragment = mWeakReference.get();
//        if (mHomeFragment != null) {
//            mHomeFragment.cancelProgressDialog("处理成功");
//        }
        ToastUtils.showToast("处理成功");
    }

    @Override
    public void onProgress(int progress, long progressTime) {
//        final HomeFragment mHomeFragment = mWeakReference.get();
//        if (mHomeFragment != null) {
//            //progressTime 可以在结合视频总时长去计算合适的进度值
//            mHomeFragment.setProgressDialog(progress, progressTime);
//        }
    }

    @Override
    public void onCancel() {
//        final HomeFragment mHomeFragment = mWeakReference.get();
//        if (mHomeFragment != null) {
//            mHomeFragment.cancelProgressDialog("已取消");
//        }
    }

    @Override
    public void onError(String message) {
//        final HomeFragment mHomeFragment = mWeakReference.get();
//        if (mHomeFragment != null) {
//            mHomeFragment.cancelProgressDialog("出错了 onError：" + message);
//        }
        ToastUtils.showToast(message);
    }
}
