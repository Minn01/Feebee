package com.example.feebee_android_project_app.accountScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccountCard(
    onAccountClicked: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .size(500.dp, 150.dp)
            .padding(bottom = 20.dp)
            .clickable(onClick = onAccountClicked)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = null,
                modifier = Modifier.size(150.dp).padding()
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(end = 20.dp)
            ) {
                Text(
                    "Account Name",
                    fontSize = 30.sp,
                )
                Text("Savings : 1341341", fontSize = 18.sp)
                Text("Budget Status: cool", fontSize = 18.sp)
            }
        }
    }
}
