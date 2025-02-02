package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.compose.rememberNavController
import com.example.feebee_android_project_app.sideNavigationDrawer.SideMenuScreen

@Composable
fun MainScreen () {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    // Navigation Drawer for the main menu
    ModalNavigationDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerContent = {
            // The content of what's being displayed in the menu
            ModalDrawerSheet(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
                drawerShape = RectangleShape
            ) {
                SideMenuScreen(
                    drawerState = drawerState,
                    navController = navController,
                    coroutineScope = coroutineScope,
                    modifier = Modifier
                )
            }
        }
    ) {
        // The content of what's being displayed in the screen
       Scaffold { innerPadding ->
           NavigationGraph(
               navController = navController,
               contentPadding = innerPadding,
               drawerState = drawerState,
               coroutineScope = coroutineScope
           )
       }
    }
}
