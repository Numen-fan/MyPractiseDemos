package com.jiajia.kotlinmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jiajia.basemodule.config.RouteConfig

@Route(path = RouteConfig.KOTLIN_MAIN_ACTIVITY)
class KotlinMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_module_activity_kotlin_main)

        findViewById<TextView>(R.id.tv_tips).setOnClickListener {
            ARouter.getInstance().build(RouteConfig.APP_DEMO_ACTIVITY).navigation();
        }

    }
}