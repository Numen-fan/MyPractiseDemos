package com.jiajia.mypractisedemos.module.mvpdemo;

/**
 * <pre>
 *  Created by fanjiajia on 2019/3/29.
 *  desc: 回调对象
 */

public class ResultBean {

    private String code;    // 状态码

    private String msg; // 消息

    private String data;    // 数据 json格式

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data == null ? "" : data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResultBean(String code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
