package com.example.feebee_android_project_app.accountScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AccountMainScreen(
    navController: NavController,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(top = 20.dp, start = 16.dp, end = 16.dp)
    ) {
        item {
            OverViewCard(
                modifier = Modifier
            )

            Button(
                onClick = {},
                modifier = Modifier.padding(top = 16.dp, bottom = 20.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)) {
                    Text("add account")
                }
            }
        }

        items(5) {
            AccountCard(
                onAccountClicked = { navController.navigate("account/1") },
                Modifier
            )
        }

    }
}
