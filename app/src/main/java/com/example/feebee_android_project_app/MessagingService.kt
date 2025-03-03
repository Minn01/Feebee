package com.example.feebee_android_project_app

import android.app.NotificationManager
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import javax.inject.Inject

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationChannel
import android.os.Build

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "Message received: ${remoteMessage.data}")

        remoteMessage.notification?.let {
            NotificationHelper.showNotification(this, it.title, it.body)
        }
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
    }
}
