package com.jiajia.mypractisedemos.module.mvpdemo.view;

import com.jiajia.mypractisedemos.module.mvpdemo.base.IBaseView;

/**
 * <pre>
 *  Created by fanjiajia on 2019/3/29.
 *  desc:
 */

public interface ILoginView extends IBaseView {

    void loginSuccess(String resultMsg);

    void loginFailure(String failureMsg);
}
