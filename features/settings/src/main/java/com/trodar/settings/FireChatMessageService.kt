package com.trodar.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class FireChatMessageService : FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.e(TAG, "From: " + message.from)

       showNotification(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    private fun showNotification(message: RemoteMessage) {
        ensureNotificationChannelExists()
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            component = ComponentName(
                packageName,
                TARGET_ACTIVITY_NAME,
            )
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val tittle = message.data["title"] ?: message.notification?.title ?: return
        val body = message.data["message"] ?: message.notification?.body ?: ""

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(tittle)
            .setContentText(body)
            .setSmallIcon(R.drawable.notification_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Random.nextInt(), notification)
    }
    private fun ensureNotificationChannelExists() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "FireChat",
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = "Test push notification"
        }
        // Register the channel with the system
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }



    companion object {
        private const val TAG = "MAcK"
        private const val CHANNEL_ID = "1"
        private const val TARGET_ACTIVITY_NAME = "com.trodar.firechat.MainActivity"
    }
}