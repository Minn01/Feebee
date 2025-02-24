package com.example.feebee_android_project_app.accountScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feebee_android_project_app.data.Account

@Composable
fun AccountCard(
    account: Account,
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
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp).padding()
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(end = 20.dp)
            ) {
                Text(
                    account.accountName,
                    fontSize = 30.sp,
                )
                Text("Savings : ${account.accountBalance}", fontSize = 18.sp)
                Text("created: ${account.createdDate}", fontSize = 18.sp)
            }
        }
    }
}
