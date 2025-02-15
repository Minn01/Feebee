package com.example.feebee_android_project_app.initialSetUpScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.feebee_android_project_app.AuthViewModel
import com.example.feebee_android_project_app.data.AppScreens

@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val userName = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Feebe", fontSize = 50.sp)

        Spacer(Modifier.height(45.dp))

        Text(
            "Create an Account",
            fontSize = 25.sp,
        )

        Spacer(Modifier.height(20.dp))

        TextField(
            value = userName.value,
            label = { Text("User Name") },
            onValueChange = {
                userName.value = it
            }
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = email.value,
            label = { Text("Email") },
            onValueChange = {
                email.value = it
            }
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = password.value,
            label = { Text("Password") },
            onValueChange = {
                password.value = it
            }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.signup(
                    email = email.value,
                    userName = userName.value,
                    password = password.value
                )
                navController.navigate(AppScreens.Loading.route)
            },
            colors = ButtonColors(
                containerColor = Color(red = 43, green = 111, blue = 213, alpha = 255),
                contentColor = Color.White,
                disabledContainerColor = LocalContentColor.current,
                disabledContentColor = LocalContentColor.current,
            )
        ) {
            Text("SignUp")
        }

        Spacer(Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate(AppScreens.Login.route) }
        ) {
            Text("Already have an account? Login")
        }
    }
}