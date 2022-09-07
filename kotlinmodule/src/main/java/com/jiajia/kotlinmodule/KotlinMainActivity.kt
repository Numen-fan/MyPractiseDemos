package com.jiajia.kotlinmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = KotlinMainActivity.PATH)
class KotlinMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_module_activity_kotlin_main)
    }

    companion object {
        const val PATH = "/KotlinModule/KotlinMainActivity"
    }
}