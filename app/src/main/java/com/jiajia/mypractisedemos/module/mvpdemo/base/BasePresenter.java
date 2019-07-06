package com.jiajia.mypractisedemos.module.mvpdemo.base;

/**
 * <pre>
 *  Created by fanjiajia on 2019/4/1.
 *  desc:
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected V view;


    @Override
    public void attach(V view) {
        this.view = view;

    }

    @Override
    public void detach() {
        this.view = null;
    }

}
