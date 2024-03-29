package com.jiajia.mypractisedemos.module.webview;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils;

public class MyJSApiPlugin extends H5SimplePlugin {

    private static final String TAG = "MyJSApiPlugin";

    /**
     * 自定义 API
     */
    public static final String TINY_TO_NATIVE = "tinyToNative";

    @Override
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        // onPrepare 中需要 add 进来
        filter.addAction(TINY_TO_NATIVE);
    }

    @Override
    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        LogUtils.INSTANCE.warn(TAG, "get event = " + event.getAction());
        String action = event.getAction();
        if (TINY_TO_NATIVE.equalsIgnoreCase(action)) {
            JSONObject params = event.getParam();
            String param1 = params.getString("param1");
            String param2 = params.getString("param2");
            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("message", "客户端接收到参数：" + param1 + ", " + param2 + "\n返回 Demo 当前包名：" + context.getActivity().getPackageName());
            context.sendBridgeResult(result);
            return true;
        }
        return false;
    }
}
