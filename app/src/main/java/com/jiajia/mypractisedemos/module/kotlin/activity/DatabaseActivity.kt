package com.jiajia.mypractisedemos.module.kotlin.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jiajia.mypractisedemos.R
import com.jiajia.mypractisedemos.module.kotlin.BaseKotlinActivity
import com.jiajia.mypractisedemos.module.kotlin.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_database.*

class DatabaseActivity : BaseKotlinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val dbHelper = DatabaseHelper(this, "BookStore.db", 3)

        btn_create_table.setOnClickListener { dbHelper.writableDatabase }

        btn_insert.setOnClickListener {
            val db = dbHelper.writableDatabase;
            val values = ContentValues().apply {
                put("name", "第一行代码")
                put("author", "郭霖")
                put("pages",454)
                put("price", 16.98)
            }

            db.insert("Book", null, values)

        }

    }

    companion object {
        private const val TAG = "DatabaseActivity"

        fun startActivity(context: Context) {
            val intent = Intent(context, DatabaseActivity::class.java)
            context.startActivity(intent)
        }
    }


}