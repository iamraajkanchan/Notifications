package com.example.notificationexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompatExtras
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity()
{

    @SuppressWarnings("unchecked")
    private val CHANNEL_ID = "channel id"
    private val CHANNEL_NAME = "channel name"
    private val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        val intent = Intent(this , MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0 , PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(this , CHANNEL_ID).setContentTitle("Awesome Notification").setContentText("This is the content text of Notification").setSmallIcon(R.drawable.ic_launcher_background).setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent).build()

        val notificationManager = NotificationManagerCompat.from(this)
        val btShowNotification = findViewById<Button>(R.id.bt_showNotification)
        btShowNotification.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID , notification)
        }

    }

    private fun createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}