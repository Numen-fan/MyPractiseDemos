package com.jiajia.mypractisedemos.module.mvpdemo.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * <pre>
 *  Created by fanjiajia on 2019/4/1.
 *  desc:
 */

public abstract class BaseMVPActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {


    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getViewId());

        mPresenter = createPresenter();

        if (mPresenter == null) {
            throw  new IllegalStateException("请实现createPresenter方法！");
        }else {
            mPresenter.attach(this);
        }

        initView();

        initData();


    }


    protected abstract T createPresenter();

    protected abstract int getViewId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
