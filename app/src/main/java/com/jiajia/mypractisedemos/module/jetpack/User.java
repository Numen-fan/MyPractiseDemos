package com.jiajia.mypractisedemos.module.jetpack;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.jiajia.mypractisedemos.BR;

/**
 * <pre>
 *  Created by fanjiajia on 2019/6/22.
 *  desc:
 */

public class User extends BaseObservable{
    private String userName;
    private int age;

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    @Bindable
    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
