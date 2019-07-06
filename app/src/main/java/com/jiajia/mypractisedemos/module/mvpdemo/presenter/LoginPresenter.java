package com.jiajia.mypractisedemos.module.mvpdemo.presenter;

import com.jiajia.mypractisedemos.module.mvpdemo.MyCallBack;
import com.jiajia.mypractisedemos.module.mvpdemo.ResultBean;
import com.jiajia.mypractisedemos.module.mvpdemo.base.BasePresenter;
import com.jiajia.mypractisedemos.module.mvpdemo.model.DataModel;
import com.jiajia.mypractisedemos.module.mvpdemo.view.ILoginView;

/**
 * <pre>
 *  Created by fanjiajia on 2019/3/29.
 *  desc:
 */

public class LoginPresenter extends BasePresenter<ILoginView> {


    private DataModel mModel;

    public LoginPresenter() {

        this.mModel = new DataModel();
    }

    /**
     * 定义View会发起的动作
     */

    public void login(String userName, String password) {

        mModel.login(userName, password, new MyCallBack<ResultBean>() {
            @Override
            public void callError(ResultBean resultBean) {

            }

            @Override
            public void callSuccess(ResultBean resultBean) {
                view.loginSuccess(resultBean.getMsg());
            }

            @Override
            public void callFailure(ResultBean resultBean) {
                view.loginFailure(resultBean.getMsg());
            }

            @Override
            public void callException(ResultBean resultBean) {

            }
        });

    }
}
