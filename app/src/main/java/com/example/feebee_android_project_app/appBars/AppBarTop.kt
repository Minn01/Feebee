package com.example.feebee_android_project_app.appBars

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTop(
    currentScreen: String,
    appTheme: State<String>,
    onTopBarIconClicked: () -> Unit,
    modifier: Modifier
) {
    CenterAlignedTopAppBar (
        title = {
            Text(
                currentScreen,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onTopBarIconClicked,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "menu_icon",
                    modifier = Modifier.size(50.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (appTheme.value == "dark") darkModeColors.barColor else lightModeColors.barColor,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    )
}
