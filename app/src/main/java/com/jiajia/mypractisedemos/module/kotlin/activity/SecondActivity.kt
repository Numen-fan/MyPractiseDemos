package com.jiajia.mypractisedemos.module.kotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import com.jiajia.mypractisedemos.R
import com.jiajia.mypractisedemos.module.kotlin.BaseKotlinActivity
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : BaseKotlinActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initListener()

    }

    private fun initListener() {
        btn_toast.setOnClickListener {
            ToastUtils.showToast("this is SecondActivity")
        }
    }

    companion object {

        private const val TAG = "SecondActivity"

        fun startActivity(@NonNull context: Context, startFrom: String, refer: String?) {
            val intent = Intent(context, SecondActivity::class.java).apply {
                putExtra("start_from", startFrom)
                putExtra("refer", refer)
            }
            context.startActivity(intent)
        }
    }


}
