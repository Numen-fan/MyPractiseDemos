package com.jiajia.mypractisedemos.module.kotlin.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.jiajia.mypractisedemos.R
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 版本8之后需要创建通知的通道channel∑
            val channel = NotificationChannel("normal",
                "Normal", NotificationManager.IMPORTANCE_DEFAULT)

            val channel2 = NotificationChannel("high",
                "High", NotificationManager.IMPORTANCE_HIGH);

            manager.createNotificationChannel(channel) // 一旦创建就不能够更改
            manager.createNotificationChannel(channel2)
        }

        btn_notification_send.setOnClickListener {
            val notification = NotificationCompat.Builder(this, "normal")
                .setContentTitle("This is notification title")
                .setStyle(NotificationCompat.BigTextStyle().bigText("This is Notification Conten,This is Notification Content,This is Notification Content"))
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources, R.drawable.hitv_mode_presenter_down)))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .build()

            manager.notify(1, notification)
        }

        btn_notification_high.setOnClickListener {
            val notification = NotificationCompat.Builder(this, "high")
                .setContentTitle("This is a high notification")
                .setStyle(NotificationCompat.BigTextStyle().bigText("This is a Notification,This is Notification Content,This is Notification Content"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .build()

            manager.notify(2, notification);
        }

    }
}