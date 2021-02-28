package com.jiajia.mypractisedemos.module.jetpack;

import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import java.util.ArrayList;

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
