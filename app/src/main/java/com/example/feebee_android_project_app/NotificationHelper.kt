package com.example.feebee_android_project_app
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
    fun showNotification(context: Context, title: String?, message: String?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "budget_alerts",
                "Budget Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Budget notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, "budget_alerts")
            .setSmallIcon(R.drawable.exchange_rate_icon_dark)
            .setContentTitle(title ?: "Budget Alert")
            .setContentText(message ?: "Budget exceeded!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(0, builder.build())
    }
}
