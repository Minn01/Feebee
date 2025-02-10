package com.example.feebee_android_project_app.data

sealed class AuthState {
    object Authenticated: AuthState()
    object UnAuthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val message: String): AuthState()
}