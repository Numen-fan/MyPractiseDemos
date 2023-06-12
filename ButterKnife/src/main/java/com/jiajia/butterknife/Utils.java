package com.jiajia.butterknife;

import android.app.Activity;
import android.view.View;

/**
 * Created by Numen_fan on 2023/3/10
 * Desc:
 */
public class Utils {

    public static <T extends View> T findViewById(Activity activity, int viewId) {
        return activity.findViewById(viewId);
    }

}
