package com.jiajia.mypractisedemos.module.kotlin

/**
 * Created by fanjiajia02 on 2021-07-11
 * Desc: Append extension methods to String class
 */

fun String.lettersCount(): Int {
    var count = 0
    for (char in this) {
        count += if (char.isLetter()) 1 else 0
    }
    return count;
}


