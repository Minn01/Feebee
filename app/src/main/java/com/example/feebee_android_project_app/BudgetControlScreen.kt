package com.example.feebee_android_project_app

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.feebee_android_project_app.ui.theme.Feebee_Android_Project_AppTheme

@Composable
fun BudgetControlScreen(
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.padding(start = 16.dp, end = 18.dp, top = 20.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Set Budget Goals",
                    fontSize = 24.sp
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "info_icon",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(30.dp)
                )
            }
        }

        items(2) {
            BudgetCard(
                budgetStatus = true,
                modifier = Modifier
            )
        }

        item {
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton (
                    onClick = {},
                    modifier = Modifier
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(10))
                        .fillMaxWidth()
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add_budget"
                        )

                        Text("Add Budget")
                    }
                }
            }
        }
    }
}

@Composable
fun BudgetCard(
    budgetStatus: Boolean,
    modifier: Modifier
) {
    val budgetStatusIcon = if (budgetStatus) Icons.Default.Check else Icons.Default.Clear
    val iconColor = if (budgetStatus) Color(0xFF029934) else Color.Red
    ElevatedCard(
        modifier = modifier
            .defaultMinSize(minHeight = 200.dp)
            .padding(top = 20.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (budgetName, statusIcon, budgetLimit, accountName, dateCreated) = createRefs()
            val startGuideLine = createGuidelineFromStart(16.dp)

            Text(
                "Budget Name",
                fontSize = 24.sp,
                modifier = Modifier.constrainAs(budgetName) {
                    start.linkTo(startGuideLine)
                    top.linkTo(parent.top, 20.dp)
                }
            )

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(2.dp, Color.Black, shape = CircleShape)
                    .constrainAs(statusIcon) {
                        top.linkTo(budgetName.top)
                        end.linkTo(parent.end, 30.dp)
                    }
            ) {
                Icon(
                    imageVector = budgetStatusIcon,
                    contentDescription = "budget_status_icon",
                    tint = iconColor,
                    modifier = Modifier.padding(5.dp).size(30.dp)
                )
            }

            TextCol(
                text1 = "Budget Limit:",
                text2 = "10000000",
                text1FontSize = 20.sp,
                text2FontSize = 18.sp,
                modifier = Modifier.constrainAs(budgetLimit) {
                    start.linkTo(startGuideLine)
                    top.linkTo(budgetName.bottom, 32.dp)
                }
            )

            TextCol(
                text1 = "Account:",
                text2 = "ACCOUNT_NAME",
                text1FontSize = 20.sp,
                text2FontSize = 18.sp,
                modifier = Modifier.constrainAs(accountName) {
                    start.linkTo(budgetLimit.end)
                    end.linkTo(parent.end)
                    top.linkTo(budgetName.bottom, 32.dp)
                }
            )

            Text(
                text = "03/10/2024",
                modifier = Modifier.constrainAs(dateCreated) {
                    start.linkTo(startGuideLine)
                    top.linkTo(budgetLimit.bottom, 20.dp)
                }
            )
        }
    }
}

@Composable
fun TextCol(
    text1: String,
    text2: String,
    text1FontSize: TextUnit,
    text2FontSize: TextUnit,
    modifier: Modifier
) {
    Column(modifier) {
        Text(
            text = text1,
            fontSize = text1FontSize
        )

        Text(
            text = text2,
            fontSize = text2FontSize
        )
    }
}

@Preview
@Composable
fun BudgetControlScreenPreview() {
    Feebee_Android_Project_AppTheme {
        Scaffold { contentPadding ->
            BudgetControlScreen(modifier = Modifier.padding(contentPadding))
        }
    }
}
