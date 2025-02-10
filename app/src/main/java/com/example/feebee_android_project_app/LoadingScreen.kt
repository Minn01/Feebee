package com.example.feebee_android_project_app

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.data.AuthState
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    navController: NavController,
    authState: AuthState
) {
    val timeoutMillis = 20_000L // Timeout after 20 seconds
    val contextForToast = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate(AppScreens.Home.route) {
                    popUpTo("loading") { inclusive = true }
                }
            }
            is AuthState.UnAuthenticated -> {
                Toast.makeText(contextForToast, "Authentication Failed", Toast.LENGTH_SHORT).show()
                navController.navigate(AppScreens.Signup.route) {
                    popUpTo("loading") { inclusive = true }
                }
            }

            is AuthState.Error -> {
                Toast.makeText(contextForToast, authState.message, Toast.LENGTH_SHORT).show()
                navController.navigate(AppScreens.Signup.route) {
                    popUpTo("loading") { inclusive = true }
                }
            }
            is AuthState.Loading -> {
                delay(timeoutMillis) // Wait for the timeout duration
                if (authState == AuthState.Loading) {
                    Toast.makeText(contextForToast, "Unexpected Error! Timed Out", Toast.LENGTH_SHORT).show()
                    navController.navigate(AppScreens.Signup.route) {
                        popUpTo("loading") { inclusive = true }
                    }
                }
            }
        }
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(Modifier.size(75.dp))
    }
}
