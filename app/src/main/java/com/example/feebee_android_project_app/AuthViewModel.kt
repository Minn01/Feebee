package com.example.feebee_android_project_app

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feebee_android_project_app.data.AuthState
import com.example.feebee_android_project_app.data.DataStoreManager
import com.example.feebee_android_project_app.data.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application): AndroidViewModel(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val dataStoreManager = DataStoreManager(application.applicationContext)

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        viewModelScope.launch {
            dataStoreManager.confirmLoginStatus().collect { isLoggedIn ->
                if (isLoggedIn && auth.currentUser != null) {
                    _authState.postValue(AuthState.Authenticated)
                } else {
                    _authState.postValue(AuthState.UnAuthenticated)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password cannot be empty!")
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }

        viewModelScope.launch {
            dataStoreManager.setLoginStatus(true)
        }
    }

    fun signup(userName: String, email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password cannot be empty!")
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }

        viewModelScope.launch {
            dataStoreManager.saveToUserDataStore(UserDetails(email, userName))
            dataStoreManager.setLoginStatus(true)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getUserDetails(): Flow<UserDetails> {
        return dataStoreManager.getUserDataFromDataStore()
    }

    fun getLoginStatus(): Flow<Boolean> {
        return dataStoreManager.confirmLoginStatus()
    }
}