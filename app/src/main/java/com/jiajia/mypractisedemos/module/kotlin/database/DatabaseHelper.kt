package com.jiajia.mypractisedemos.module.kotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.jiajia.mypractisedemos.module.kotlin.util.LogUtils
import com.jiajia.mypractisedemos.module.kotlin.util.ToastUtils

/**
 * Created by fanjiajia02 on 2021-07-15
 * Desc:
 */
class DatabaseHelper(context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createTableBookSql = "create table Book(" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text)"

    private val createCategorySql ="create table Category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTableBookSql)
        db?.execSQL(createCategorySql) // 这里必须也要添加，因为新安装的程序需要
        ToastUtils.showToast("Create table Book success!")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        LogUtils.debug(TAG, "newVersion is $newVersion")
        if (oldVersion <= 1) { // 升级到2时执行
            db?.execSQL(createCategorySql)
        }

        if (oldVersion <= 2) { // 升级到3时执行
            db?.execSQL("alter table Book add column category_id integer")
        }
    }

    companion object {

        private const val TAG = "DatabaseHelper"

    }


}