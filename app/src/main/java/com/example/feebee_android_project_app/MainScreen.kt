package com.example.feebee_android_project_app

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feebee_android_project_app.accountScreens.AccountBarTop
import com.example.feebee_android_project_app.appBars.AppBarBottom
import com.example.feebee_android_project_app.appBars.AppBarTop
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.data.RoomViewModel
import com.example.feebee_android_project_app.data.UserDetails
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors
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
    val appTheme = authViewModel.themeState.collectAsState()
    authViewModel.getAppTheme()
    val selectedTab = rememberSaveable { mutableIntStateOf(0) }

    val roomViewModel: RoomViewModel = hiltViewModel()
    val accountsList = roomViewModel.accountList.collectAsState()
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val accountNameOnTopBar = rememberSaveable { mutableStateOf("") }

    // Navigation Drawer for the main menu
    ModalNavigationDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerContent = {
            // The content of what's being displayed in the menu
            ModalDrawerSheet(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
                drawerShape = RectangleShape,
                drawerContainerColor = if (appTheme.value == "dark") darkModeColors.backgroundColor else lightModeColors.backgroundColor,
                drawerContentColor = if (appTheme.value == "dark") darkModeColors.contentColor else lightModeColors.contentColor
            ) {
                SideMenuScreen(
                    appTheme = appTheme,
                    drawerState = drawerState,
                    selectedTab = selectedTab,
                    navController = navController,
                    coroutineScope = coroutineScope,
                    userDetails = authViewModel.getUserDetails().collectAsState(initial = UserDetails()),
                    onAppThemeButtonPressed = {
                        val changeTheme = when(appTheme.value) {
                            "light" -> "dark"
                            "dark" -> "light"
                            else -> "light"
                        }

                        authViewModel.setAppTheme(changeTheme)
                    },
                    modifier = Modifier
                )
            }
        }
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val selectedItem = rememberSaveable { mutableIntStateOf(0) }
        val currentRoute = currentBackStackEntry?.destination?.route
        val currentScreen = getScreenTitle(currentRoute ?: "")

        Log.d("currentScreen", "currentScreen: $currentScreen")
        // The content of what's being displayed in the screen
       Scaffold (
           topBar =  {
               if (currentScreen == "SINGLE_ACCOUNT") {
                   AccountBarTop(
                       accountNameOnTopBar = accountNameOnTopBar,
                       appTheme = appTheme,
                       modifier = Modifier,
                       accountLists = accountsList,
                       selectedIndex = selectedIndex,
                       onIndexChanged = { newIndex ->
                           selectedIndex.intValue = newIndex
                       },
                       onAccountSelected = { account ->
                           accountNameOnTopBar.value = account.accountName
                           navController.navigate("account/${account.accountId}")
                       },
                       onTopBarIconClicked = {
                           coroutineScope.launch {
                               drawerState.open()
                           }
                       }
                   )
               } else if (currentScreen != "SETUP") {
                   AppBarTop(
                       currentScreen = currentScreen,
                       onTopBarIconClicked = {
                           coroutineScope.launch {
                               drawerState.open()
                           }
                       },
                       appTheme = appTheme,
                       modifier = Modifier
                   )
               }
           },

           bottomBar = {
               if (currentScreen != "SETUP") {
                   AppBarBottom(
                       appTheme = appTheme,
                       selectedItem = selectedItem,
                       selectedTab = selectedTab,
                       navController = navController,
                       modifier = Modifier
                   )
               }
           },

           containerColor = if (appTheme.value == "dark") darkModeColors.backgroundColor else lightModeColors.backgroundColor,
           contentColor = if (appTheme.value == "dark") darkModeColors.contentColor else lightModeColors.contentColor,

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
    if (currentRoute.startsWith("account/")) {
        return "SINGLE_ACCOUNT"
    }

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
