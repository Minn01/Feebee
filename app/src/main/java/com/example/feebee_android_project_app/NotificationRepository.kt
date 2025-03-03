package com.example.feebee_android_project_app

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val context: Context
) {

    fun sendBudgetExceededNotification(spentAmount: Double, budgetAmount: Double) {
        val message = "You've exceeded your budget! Spent: $$spentAmount Budget: $$budgetAmount"
        NotificationHelper.showNotification(context, "Budget Alert", message)
    }

    fun getFcmToken(onTokenReceived: (String) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM token failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.d("FCM", "FCM Token: $token")
                onTokenReceived(token)
            }
    }
}