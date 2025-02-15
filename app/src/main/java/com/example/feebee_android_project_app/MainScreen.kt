package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feebee_android_project_app.appBars.AppBarBottom
import com.example.feebee_android_project_app.appBars.AppBarTop
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.data.UserDetails
import com.example.feebee_android_project_app.sideNavigationDrawer.SideMenuScreen
import kotlinx.coroutines.launch

@Composable
fun MainScreen (
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val pagerState = rememberPagerState(pageCount = { 4 })

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
                    userDetails = authViewModel.getUserDetails().collectAsState(initial = UserDetails()),
                    modifier = Modifier
                )
            }
        }
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val selectedItem = rememberSaveable { mutableIntStateOf(0) }
        val currentRoute = currentBackStackEntry?.destination?.route
        val currentScreen = getScreenTitle(currentRoute ?: "")

        // The content of what's being displayed in the screen
       Scaffold (
           topBar =  {
               if (currentScreen != "SETUP") {
                   AppBarTop(
                       currentScreen = currentScreen,
                       onTopBarIconClicked = {
                           coroutineScope.launch {
                               drawerState.open()
                           }
                       },
                       modifier = Modifier
                   )
               }
           },

           bottomBar = {
               if (currentScreen != "SETUP") {
                   AppBarBottom(
                       selectedItem = selectedItem,
                       navController = navController,
                       modifier = Modifier
                   )
               }
           }

       ) { innerPadding ->
           NavigationGraph(
               authViewModel = authViewModel,
               navController = navController,
               isLoggedIn = authViewModel.getLoginStatus().collectAsState(initial = false),
               contentPadding = innerPadding
           )
       }
    }
}

fun getScreenTitle(currentRoute: String): String {
    return when (currentRoute) {
        AppScreens.Home.route -> AppScreens.Home.title
        AppScreens.ExchangeRate.route -> AppScreens.ExchangeRate.title
        AppScreens.Accounts.route -> AppScreens.Accounts.title
        AppScreens.Settings.route -> AppScreens.Settings.title
        AppScreens.Profile.route -> AppScreens.Profile.title
        AppScreens.LanguageSupport.route -> AppScreens.LanguageSupport.title
        AppScreens.Guide.route -> AppScreens.Guide.title
        AppScreens.Notifications.route -> AppScreens.Notifications.title
        AppScreens.BudgetControl.route -> AppScreens.BudgetControl.title
        AppScreens.Signup.route, AppScreens.Login.route, AppScreens.Loading.route -> "SETUP"
        else -> "UNDEFINED"
    }
}
