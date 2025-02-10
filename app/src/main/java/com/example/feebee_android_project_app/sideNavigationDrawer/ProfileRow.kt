package com.example.feebee_android_project_app.sideNavigationDrawer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feebee_android_project_app.R
import com.example.feebee_android_project_app.data.AppScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProfileRow (
    userName: String,
    userEmail: String,
    coroutineScope: CoroutineScope,
    navController: NavController,
    drawerState: DrawerState,
    modifier: Modifier
) {
    Row(
        modifier = modifier.padding(start = 16.dp, top = 50.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.default_avatar),
            contentDescription = "default_avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
                .clickable {
                    coroutineScope.launch {
                        drawerState.close()
                    }

                    navController.navigate(AppScreens.Profile.route)
                }
        )

        Log.d("EA", "EA2 reached")

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = userName,
            )

            Text(
                text = userEmail,
            )
        }
    }
}
