package com.jiajia.mypractisedemos.module.kotlin.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 单例
object ServiceCreator {

    private const val BASE_URL = "http://172.21.223.52"

    /**
     * private对外界不可见
     */
    private val  retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}