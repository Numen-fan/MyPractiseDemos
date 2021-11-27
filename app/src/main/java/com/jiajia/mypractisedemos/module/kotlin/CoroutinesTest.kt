package com.jiajia.mypractisedemos.module.kotlin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    GlobalScope.launch {
        println("codes run in coroutine scope!")
    }
    Thread.sleep(1000)

    runBlocking {
        repeat(10) {
            println("打印10次")
        }
    }

    runBlocking {
        val result = async {
            5 + 5
        }.await()
        println(result)
    }

}
