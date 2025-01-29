package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    drawerState.open()
                }
            }
        ) { Text("Open Drawer") }
    }
}
