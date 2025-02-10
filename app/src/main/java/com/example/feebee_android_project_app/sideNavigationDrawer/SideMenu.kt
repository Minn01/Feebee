package com.example.feebee_android_project_app.sideNavigationDrawer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feebee_android_project_app.R
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.data.UserDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
    This is what is being displayed in the navigation drawer or the side menu
 */

@Composable
fun SideMenuScreen(
    drawerState: DrawerState,
    navController: NavController,
    coroutineScope: CoroutineScope,
    userDetails: State<UserDetails>,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Log.d("EA", "EA1 reached")
        // Here should be the profile row or box
        ProfileRow(
            userName = userDetails.value.userName,
            userEmail = userDetails.value.email,
            navController = navController,
            drawerState = drawerState,
            coroutineScope = coroutineScope,
            modifier = Modifier
        )

        // The rest
        NavigationDrawerItem(
            label = { Text("Settings") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "settings"
                )
            },
            selected = false,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }

                navController.navigate(AppScreens.Settings.route)
            }
        )

        NavigationDrawerItem(
            label = { Text("Notifications") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "notifications"
                )
            },
            selected = false,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }

                navController.navigate(AppScreens.Notifications.route)
            }
        )

        NavigationDrawerItem(
            label = { Text("Exchange Rate") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.exchange_rate_icon_dark),
                    contentDescription = "settings"
                )
            },
            selected = false,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }

                navController.navigate(AppScreens.ExchangeRate.route)
            }
        )

        NavigationDrawerItem(
            label = { Text("Guide / Help") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "settings"
                )
            },
            selected = false,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }

                navController.navigate(AppScreens.ExchangeRate.route)
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp))
        ColorSchemeBox(
            modifier = Modifier
        )
        Spacer(Modifier.height(30.dp))
    }
}

