package com.example.feebee_android_project_app.accountScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

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
