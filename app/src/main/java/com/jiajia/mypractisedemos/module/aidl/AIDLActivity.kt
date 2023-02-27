package com.jiajia.mypractisedemos.module.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import com.jiajia.mypractisedemos.R
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils

class AIDLActivity : AppCompatActivity() {

    private val TAG = "AIDLActivity"

    private var iBookAidlInterface: IBookAidlInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidlactivity)
        bindService()

        findViewById<Button>(R.id.btn_title).setOnClickListener {
            LogUtils.debug(TAG, "再去一次呢：${iBookAidlInterface?.title}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    private fun bindService() {
        val intent = Intent(this, MyAIDLService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private var connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iBookAidlInterface = IBookAidlInterface.Stub.asInterface(service)
            var title = iBookAidlInterface?.title
            LogUtils.warn(TAG, "getTitle is $title")
            iBookAidlInterface?.title = "我给你设置了一个Title"
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }
}