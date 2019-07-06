package com.jiajia.mypractisedemos.module.mvpdemo;

/**
 * <pre>
 *  Created by fanjiajia on 2019/3/29.
 *  desc:
 */

public interface MyCallBack<ResultBean>{

    void callError(ResultBean bean);

    void callSuccess(ResultBean bean);

    void callFailure(ResultBean bean);

    void callException(ResultBean bean);


}
