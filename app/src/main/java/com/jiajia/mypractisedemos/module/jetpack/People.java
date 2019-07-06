package com.jiajia.mypractisedemos.module.jetpack;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  Created by fanjiajia on 2019/6/22.
 *  desc:
 */

public class People extends BaseObservable {

    public ObservableField<String> name = new ObservableField<>();
    public ObservableInt age = new ObservableInt();
    public ObservableList<String> list = new ObservableArrayList<>();

    public People(String name, int age, ArrayList<String> list) {
        this.name = new ObservableField<>(name);
        this.age = new ObservableInt(age);
        this.list = (ObservableArrayList)list;
    }

}
