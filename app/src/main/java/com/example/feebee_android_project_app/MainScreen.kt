package com.example.feebee_android_project_app

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.sideNavigationDrawer.SideMenuScreen
import kotlinx.coroutines.launch

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
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = when (currentBackStackEntry?.destination?.route) {
            AppScreens.Home.route -> AppScreens.Home.title
            AppScreens.ExchangeRate.route -> AppScreens.ExchangeRate.title
            AppScreens.Accounts.route -> AppScreens.Accounts.title
            AppScreens.Settings.route -> AppScreens.Settings.title
            AppScreens.Profile.route -> AppScreens.Profile.title
            AppScreens.LanguageSupport.route -> AppScreens.LanguageSupport.title
            AppScreens.Guide.route -> AppScreens.Guide.title
            AppScreens.Notifications.route -> AppScreens.Notifications.title
            AppScreens.BudgetControl.route -> AppScreens.BudgetControl.title
            else -> "UNDEFINED"

        }

        // The content of what's being displayed in the screen
       Scaffold (
           topBar =  {
               AppBarTop(
                   currentScreen = currentScreen,
                   onTopBarIconClicked = {
                       coroutineScope.launch {
                           drawerState.open()
                       }
                   },
                   modifier = Modifier
               )
           },

           bottomBar = {
               BottomAppBar(
                   actions = {
                       BottomAppBar(
                           actions = {
                               val selectedItem = rememberSaveable { mutableIntStateOf(0) }

                               Row(
                                   modifier = Modifier.fillMaxSize(),
                                   verticalAlignment = Alignment.CenterVertically,
                                   horizontalArrangement = Arrangement.SpaceEvenly
                               ) {

                                   listOf(
                                       R.drawable.home_icon_dark to AppScreens.Home.route,
                                       R.drawable.accounts_icon_dark to AppScreens.Accounts.route,
                                       R.drawable.double_arrow_black to AppScreens.QuickActionScreen.route,
                                       R.drawable.exchange_rate_icon_dark to AppScreens.ExchangeRate.route,
                                       R.drawable.budget_icon_dark to AppScreens.BudgetControl.route,
                                   ).forEachIndexed { index, itemPair ->
                                       if (index == 2) {
                                           QuickActionButton(
                                               iconLabel = itemPair.second,
                                               onButtonClicked = {
                                                   navController.navigate(itemPair.second)
                                               },
                                               modifier = Modifier
                                           )
                                       } else {
                                           BottomNavButton(
                                               iconImage = itemPair.first,
                                               iconLabel = itemPair.second,
                                               isSelected = selectedItem.intValue == index,
                                               onButtonClicked = {
                                                   selectedItem.intValue = index
                                                   navController.navigate(itemPair.second)
                                               },
                                               modifier = Modifier
                                           )
                                       }
                                   }
                               }
                           }
                       )
                   }
               )
           }

       ) { innerPadding ->
           NavigationGraph(
               navController = navController,
               contentPadding = innerPadding,
           )
       }
    }
}

@Composable
fun BottomNavButton(
    @DrawableRes iconImage: Int,
    iconLabel: String,
    isSelected: Boolean,
    onButtonClicked: () -> Unit,
    modifier: Modifier
) {
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else Color.Gray,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ), label = "content_color"
    )

    Column(
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onButtonClicked)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconImage),
            contentDescription = iconLabel,
            tint = contentColor,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun QuickActionButton(
    iconLabel: String,
    onButtonClicked: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onButtonClicked)
            .background(Color(0xFFECAD00)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = iconLabel,
            tint = Color.White,
            modifier = Modifier.size(50.dp)
        )
    }
}
