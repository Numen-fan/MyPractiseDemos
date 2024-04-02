package com.jiajia.mypractisedemos.utils;

import java.io.File;

public class Constant {

    /**权限相关*/
    public static final String ACTION_MANAGE_OVERLAY_PERMISSION = "android.settings.action.MANAGE_OVERLAY_PERMISSION";

    public static final String URL_REGEX = "(((http|ftp|https)://)|(www\\.))[a-zA-Z0-9\\._-]+\\."
            + "[a-zA-Z]{2,6}(:[0-9]{1,4})?(/[a-zA-Z0-9\\&%_\\./-~-]*)?";

    public static final String APP_ROOT_PATH = Utils.getAppRootPath();

    public static final String APP_VIDEO_PATH = APP_ROOT_PATH + File.separator + "video";
}
