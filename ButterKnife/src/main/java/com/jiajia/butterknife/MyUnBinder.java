package com.jiajia.butterknife;

import androidx.annotation.UiThread;

/**
 * Created by Numen_fan on 2023/3/8
 * Desc:
 */
public interface MyUnBinder {
    @UiThread
    void unbind();

    MyUnBinder EMPTY = () -> {
    };
}
