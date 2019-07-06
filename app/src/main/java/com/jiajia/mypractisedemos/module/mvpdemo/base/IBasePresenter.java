package com.jiajia.mypractisedemos.module.mvpdemo.base;

/**
 * <pre>
 *  Created by fanjiajia on 2019/4/1.
 *  desc:
 */

public interface IBasePresenter<V> {


    void attach(V view);

    void detach();
}
