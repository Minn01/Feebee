package com.example.feebee_android_project_app.sideNavigationDrawer

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.feebee_android_project_app.data.AppScreens
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
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        // Here should be the profile row or box

        // The rest
        HorizontalDivider()
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

                navController.navigate(AppScreens.Settings.screen)
            }
        )

        HorizontalDivider()
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

                navController.navigate(AppScreens.Settings.screen)
            }
        )
    }
}

