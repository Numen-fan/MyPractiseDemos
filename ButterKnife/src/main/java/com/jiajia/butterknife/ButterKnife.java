package com.jiajia.butterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * Created by Numen_fan on 2023/3/7
 * Desc:
 */
public class ButterKnife {

    public static MyUnBinder bind(Activity activity) {
        try {
            Class<? extends MyUnBinder> activityClassName = (Class<? extends MyUnBinder>) Class.forName(activity.getClass().getName() + "_ViewBinding");
            Constructor<? extends MyUnBinder> binderConstructor = activityClassName.getDeclaredConstructor(activity.getClass());
            return binderConstructor.newInstance(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MyUnBinder.EMPTY;
    }

}
