package com.jiajia.mypractisedemos.module.mvpdemo.model;

import com.jiajia.mypractisedemos.module.mvpdemo.MyCallBack;
import com.jiajia.mypractisedemos.module.mvpdemo.ResultBean;

/**
 * <pre>
 *  Created by fanjiajia on 2019/3/29.
 *  desc:
 */

public class DataModel {



    public void login(String userName, String Password, MyCallBack<ResultBean> callBack) {

        ResultBean bean; // 封装返回的bean

        // 登录验证
        if (userName.equals("张三") && Password.equals("123456")) {

           bean = new ResultBean("200", "登录成功", "");

            callBack.callSuccess(bean);
        }else {
            bean = new ResultBean("110", "登录失败", "");
            callBack.callFailure(bean);
        }
    }


}
