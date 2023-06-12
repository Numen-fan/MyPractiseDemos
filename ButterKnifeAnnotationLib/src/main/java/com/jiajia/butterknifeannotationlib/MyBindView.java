package com.jiajia.butterknifeannotationlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来注入View
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS) // 编译时注解
public @interface MyBindView {
    int value(); // @MyBindView() // 括号中的id
}