package com.jiajia.mypractisedemos.module.kotlin.entity

abstract class BaseResponse<T> {

    var code: Int = 0;

    var msg: String = ""

    var data: T? = null

}