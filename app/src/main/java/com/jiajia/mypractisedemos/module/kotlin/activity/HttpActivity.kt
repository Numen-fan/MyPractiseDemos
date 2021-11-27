package com.jiajia.mypractisedemos.module.kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jiajia.mypractisedemos.R
import com.jiajia.mypractisedemos.module.kotlin.entity.App
import com.jiajia.mypractisedemos.module.kotlin.http.AppService
import com.jiajia.mypractisedemos.module.kotlin.http.ServiceCreator
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils
import kotlinx.android.synthetic.main.activity_http.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)

        btn_getAppData.setOnClickListener {
            // 写法1
//            val appService = ServiceCreator.create(AppService::class.java) // 这样的话，一个借口就必须是一个interface吗

            // 写法2
            val appService = ServiceCreator.create<AppService>()
            appService.getAppData().enqueue(object : Callback<List<App>> {
                override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                    val list = response.body()
                    if (list != null) {
                        for (app in list) {
                            LogUtils.debug(TAG, "id is ${app.id}")
                            LogUtils.debug(TAG, "name is ${app.name}")
                            LogUtils.debug(TAG, "version is ${app.version}")
                        }
                    }
                }

                override fun onFailure(call: Call<List<App>>, t: Throwable) {
                    LogUtils.error(TAG, "Get App data error!", t)
                }
            })
        }
    }
    
    companion object {
        private const val TAG = "HttpActivity"

    }
}