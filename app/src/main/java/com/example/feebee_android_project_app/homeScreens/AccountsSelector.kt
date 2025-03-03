package com.example.feebee_android_project_app.homeScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.feebee_android_project_app.data.Account
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors

@Composable
fun AccountSelector(
    accountLists: State<List<Account>>,
    selectedIndex: MutableState<Int>,
    appTheme: State<String>,
    onAccountSelected: (Account) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(top = 20.dp, end = 20.dp, bottom = 20.dp),
        colors = CardColors(
            contentColor = if (appTheme.value == "light") lightModeColors.contentColor else darkModeColors.contentColor,
            containerColor = if (appTheme.value == "light") lightModeColors.backgroundColor else lightModeColors.contentColor,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { if (selectedIndex.value > 0) selectedIndex.value-- },
                enabled = selectedIndex.value > 0
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Previous Account")
            }

            Text(
                text = accountLists.value.getOrNull(selectedIndex.value)?.accountName
                    ?: "No Account",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )

            IconButton(
                onClick = { if (selectedIndex.value < accountLists.value.lastIndex) selectedIndex.value++ },
                enabled = selectedIndex.value < accountLists.value.lastIndex
            ) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Next Account")
            }
        }
    }
}
