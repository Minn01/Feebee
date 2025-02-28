package com.example.feebee_android_project_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feebee_android_project_app.data.AuthState
import com.example.feebee_android_project_app.data.DataStoreManager
import com.example.feebee_android_project_app.data.UserDetails
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _themeState = MutableStateFlow("")
    val themeState: StateFlow<String> = _themeState

    init {
        checkAuthStatus()
        getAppTheme()
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
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
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
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }

        viewModelScope.launch {
            dataStoreManager.saveToUserDataStore(UserDetails(email, userName))
            dataStoreManager.setLoginStatus(true)
        }
    }

    fun checkTokenValidity() {
        val user = auth.currentUser
        user?.getIdToken(true)?.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                // Token is invalid or expired, log the user out
                signOut()
            }
        }
    }


    fun signOut() {
        auth.signOut()
        viewModelScope.launch {
            dataStoreManager.setLoginStatus(false)
        }
        _authState.postValue(AuthState.UnAuthenticated)
    }

    fun getUserDetails(): Flow<UserDetails> {
        return dataStoreManager.getUserDataFromDataStore()
    }

    fun getLoginStatus(): Flow<Boolean> {
        return dataStoreManager.confirmLoginStatus()
    }

    fun getAppTheme() {
        viewModelScope.launch {
            dataStoreManager.getAppThemeFromDataStore().collect {
                _themeState.value = it
            }
        }
    }

    fun setAppTheme(theme: String) {
        viewModelScope.launch {
            dataStoreManager.setAppTheme(theme)
        }
    }

    fun clearDataStore() {
        viewModelScope.launch {
            dataStoreManager.clearDataFromDataStore()
        }
    }
}
