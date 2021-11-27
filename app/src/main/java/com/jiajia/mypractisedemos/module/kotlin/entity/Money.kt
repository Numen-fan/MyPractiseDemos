package com.jiajia.mypractisedemos.module.kotlin.entity

/**
 * Created by fanjiajia02 on 2021-07-11
 * Desc:
 */
class Money(val value: Int) {

    operator fun plus(money: Money): Money {
        val sum = value + money.value
        return Money(sum)
    }

    operator fun plus(newValue: Int): Money {
        return Money(value + newValue)
    }
}