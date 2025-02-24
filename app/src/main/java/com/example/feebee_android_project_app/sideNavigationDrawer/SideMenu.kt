package com.example.feebee_android_project_app.sideNavigationDrawer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.feebee_android_project_app.AuthViewModel
import com.example.feebee_android_project_app.R
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.data.UserDetails
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors
import com.example.feebee_android_project_app.getScreenTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
    This is what is being displayed in the navigation drawer or the side menu
 */

@Composable
fun SideMenuScreen(
    appTheme: State<String>,
    selectedTab: MutableIntState,
    drawerState: DrawerState,
    navController: NavController,
    coroutineScope: CoroutineScope,
    userDetails: State<UserDetails>,
    onAppThemeButtonPressed: () -> Unit,
    modifier: Modifier
) {
    val contentColor = if (appTheme.value == "dark") darkModeColors.contentColor else lightModeColors.contentColor

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
            selected = selectedTab.intValue == 1,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                selectedTab.intValue = 1
                navController.navigate(AppScreens.Settings.route)
            },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedTextColor = contentColor,
                unselectedIconColor = contentColor,
                selectedContainerColor = Color.White
            )
        )

        NavigationDrawerItem(
            label = { Text("Notifications") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "notifications"
                )
            },
            selected = selectedTab.intValue == 2,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                selectedTab.intValue = 2
                navController.navigate(AppScreens.Notifications.route)
            },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedTextColor = contentColor,
                unselectedIconColor = contentColor,
                selectedContainerColor = Color.White
            )
        )

        NavigationDrawerItem(
            label = { Text("Exchange Rate") },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.exchange_rate_icon_dark),
                    contentDescription = "exchange_rate"
                )
            },
            selected = selectedTab.intValue == 3,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                selectedTab.intValue = 3
                navController.navigate(AppScreens.ExchangeRate.route)
            },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedTextColor = contentColor,
                unselectedIconColor = contentColor,
                selectedContainerColor = Color.White
            )
        )

        NavigationDrawerItem(
            label = { Text("Guide / Help") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "guide"
                )
            },
            selected = selectedTab.intValue == 4,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
                selectedTab.intValue = 4
                navController.navigate(AppScreens.ExchangeRate.route)
            },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedTextColor = contentColor,
                unselectedIconColor = contentColor,
                selectedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 20.dp))
        ColorSchemeBox(
            appTheme = appTheme,
            toggleButtonClicked = onAppThemeButtonPressed,
            modifier = Modifier
        )
        Spacer(Modifier.height(30.dp))
    }
}
