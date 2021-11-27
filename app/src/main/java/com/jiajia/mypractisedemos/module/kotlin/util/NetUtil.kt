package com.jiajia.mypractisedemos.module.kotlin.util

import com.jiajia.mypractisedemos.module.kotlin.entity.BaseResponse
import java.net.HttpURLConnection
import java.net.URL

class NetUtil {

    interface Callback<T :BaseResponse<T>> {

        fun onSuccess(data: T)

        fun onFailed(code: Int, msg: String)

    }

    companion object {

    }

}