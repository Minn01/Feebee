package com.example.feebee_android_project_app

import android.content.Context
import androidx.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _notifications = MutableStateFlow<List<String>>(emptyList())
    val notifications: StateFlow<List<String>> = _notifications.asStateFlow()

    init {
        notificationRepository.getFcmToken { token ->
            Log.d("FCM", "Received FCM Token: $token")
        }
    }

    fun addNotification(message: String) {
        _notifications.value = _notifications.value + message
    }
}

