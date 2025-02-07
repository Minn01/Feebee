package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.feebee_android_project_app.ui.theme.Feebee_Android_Project_AppTheme

@Composable
fun AccountMainScreen(
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
            AccountCard(Modifier)
        }

    }
}

@Composable
fun OverViewCard(
    modifier: Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth().defaultMinSize(minHeight = 200.dp)
    ) {
        ConstraintLayout {
            val (accountNumText, totalOverallTextCol, doubleCol) = createRefs()
            val startGuideLine = createGuidelineFromStart(20.dp)
            Text(
                "Accounts: 100",
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(accountNumText) {
                    start.linkTo(startGuideLine)
                    top.linkTo(parent.top, 30.dp)
                }
            )

            Column(
                modifier = Modifier.constrainAs(totalOverallTextCol) {
                    top.linkTo(accountNumText.bottom, 20.dp)
                    start.linkTo(startGuideLine)
                }
            ) {
                Text(
                    "Total:",
                    fontSize = 35.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    "100000",
                    fontSize = 35.sp,
                    modifier = Modifier
                )
            }

            Column(
                modifier = Modifier.constrainAs(doubleCol) {
                    top.linkTo(accountNumText.bottom, 20.dp)
                    start.linkTo(totalOverallTextCol.end, 40.dp)
                    end.linkTo(parent.end, 20.dp)
                }
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text("Income Today:", fontSize = 20.sp)
                    Text("1000000")
                }

                Column {
                    Text("Expense Today:", fontSize = 20.sp)
                    Text("1000000")
                }
            }
        }
    }

}

@Composable
fun AccountCard(
    modifier: Modifier
) {
    Card(
        modifier = modifier.size(500.dp, 150.dp).padding(bottom = 20.dp)
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


@Preview
@Composable
fun AccountsMainScreenPreview() {
    Feebee_Android_Project_AppTheme {
    }
}
