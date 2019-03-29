package com.jiajia.mypractisedemos.module.mvpdemo;

/**
 * <pre>
 *  Created by fanjiajia on 2019/3/29.
 *  desc:
 */

public class DataPresenter {

    private iView mView;

    private DataModel mModel;

    public DataPresenter(iView view) {
        this.mView = view;
        this.mModel = new DataModel();
    }

    /**
     * 定义View会发起的动作
     */

    public void login(String userName, String password) {

        mModel.login(userName, password, new iCallBack<ResultBean>() {
            @Override
            public void callError(ResultBean resultBean) {

            }

            @Override
            public void callSuccess(ResultBean resultBean) {
                mView.loginSuccess(resultBean.getMsg());
            }

            @Override
            public void callFailure(ResultBean resultBean) {
                mView.loginFailure(resultBean.getMsg());
            }

            @Override
            public void callException(ResultBean resultBean) {

            }
        });

    }

}
