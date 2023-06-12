package com.jiajia.mypractisedemos.module.webview;

import android.net.Uri;
import android.os.Build;

import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.provider.H5NebulaFileProvider;
import com.alipay.mobile.nebula.util.H5Log;

import java.io.File;

public class H5NebulaFileProviderImpl implements H5NebulaFileProvider {

    private static final String TAG = "H5NebulaFileProviderImpl";
    @Override
    public Uri getUriForFile(File file) {
        try {
            return getUriForFileImpl(file);
        } catch (Exception e) {
            H5Log.e(TAG, e);
        }
        return null;
    }

    private static Uri getUriForFileImpl(File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = NebulaDemoFileProvider.getUriForFile(LauncherApplicationAgent.getInstance().getApplicationContext(), LauncherApplicationAgent.getInstance().getPackageName() + ".provider", file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }
}
