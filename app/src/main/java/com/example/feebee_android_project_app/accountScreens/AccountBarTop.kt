package com.example.feebee_android_project_app.accountScreens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feebee_android_project_app.data.Account
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors
import com.example.feebee_android_project_app.ui.theme.Feebee_Android_Project_AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBarTop(
    onTopBarIconClicked: () -> Unit,
    accountNameOnTopBar: State<String>,
    accountLists: State<List<Account>>,
    selectedIndex: MutableIntState,
    onIndexChanged: (Int) -> Unit,
    onAccountSelected: (Account) -> Unit,
    appTheme: State<String>,
    modifier: Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous Button
                IconButton(
                    onClick = {
                        if (selectedIndex.intValue > 0) {
                            val newIndex = selectedIndex.intValue - 1
                            onIndexChanged(newIndex)
                            onAccountSelected(accountLists.value[newIndex])
                        }
                    },
                    enabled = selectedIndex.intValue > 0
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Previous Account"
                    )
                }

                // Account Name Display
                Log.d("account", accountNameOnTopBar.value)
                Text(
                    text = accountNameOnTopBar.value,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )

                // Next Button
                IconButton(
                    onClick = {
                        if (selectedIndex.intValue < accountLists.value.lastIndex) {
                            val newIndex = selectedIndex.intValue + 1
                            onIndexChanged(newIndex)
                            onAccountSelected(accountLists.value[newIndex])
                        }
                    },
                    enabled = selectedIndex.intValue < accountLists.value.lastIndex
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Next Account"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (appTheme.value == "dark") darkModeColors.barColor else lightModeColors.barColor,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
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
        modifier = Modifier
    )
}

@Preview
@Composable
fun AccountBarTopPreview() {
    Feebee_Android_Project_AppTheme {
        Box(Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
//            AccountBarTop(Modifier)
        }
    }
}
