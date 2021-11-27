package com.jiajia.mypractisedemos.module.kotlin.http

import com.jiajia.mypractisedemos.module.kotlin.entity.App
import retrofit2.Call
import retrofit2.http.GET

interface AppService {

    @GET("data.json")
    fun getAppData(): Call<List<App>>

}