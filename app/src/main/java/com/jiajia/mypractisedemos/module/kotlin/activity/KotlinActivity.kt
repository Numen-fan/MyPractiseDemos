package com.jiajia.mypractisedemos.module.kotlin.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.jiajia.mypractisedemos.R
import com.jiajia.mypractisedemos.module.kotlin.BaseKotlinActivity
import com.jiajia.mypractisedemos.module.kotlin.doSomething
import com.jiajia.mypractisedemos.module.kotlin.showToast
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : BaseKotlinActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        btn_kotlin_page_commit.setOnClickListener {
            Log.d(TAG, "the view's id is ${it.id}")
            "你好啊，Kotlin ${et_kotlin_page_username.text} taskId is $taskId".showToast()

        }

        doSomething()

        btn_start_second_activity.setOnClickListener {
            SecondActivity.startActivity(this, TAG, "replay")
        }

        btn_start_database_activity.setOnClickListener { DatabaseActivity.startActivity(this) }
    }

    private fun print1(name: String, age: Int) {
        var char: Char = '1'
        char.isLetter()

//        name.lettersCount()

    }

    companion object {
        const val TAG = "KotlinActivity"
    }

    inner class Person(var name: String, val age: Int) {

        constructor(name: String) : this(name, 0) {
            this.name = name
        }


    }

}