package com.example.feebee_android_project_app.budgetScreens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feebee_android_project_app.AuthViewModel
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors

@Composable
fun BudgetControlScreen(
    modifier: Modifier
) {
    val budgetViewModel: BudgetViewModel = hiltViewModel()
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val budgetAmount = budgetViewModel.budgetAmount.collectAsState()


    if (budgetAmount.value == 0.0) {
        InitialBudgetScreen(modifier) {
            showDialog.value = !showDialog.value
        }
    } else {
        Log.d("BCrl1", "code reaches here")
        MainBudgetScreen(
            modifier = Modifier,
            showDialog = showDialog,
        )
    }

    if (showDialog.value) {
        BudgetSetupDialog(
            onDismiss = {
                showDialog.value = !showDialog.value
            },
            onConfirm = { budget, period ->
                budgetViewModel.setBudget(budget)
                budgetViewModel.setBudgetCycle(period)
            }
        )
    }
}

@Composable
fun InitialBudgetScreen(modifier: Modifier, onButtonClicked: () -> Unit) {
    Box(modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        FloatingActionButton(
            onClick = onButtonClicked
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.padding(start = 10.dp))
                Text("Add Budget Plan", modifier = Modifier.padding(start = 5.dp, end = 16.dp, top = 16.dp, bottom = 16.dp))
            }
        }
    }
}

@Composable
fun MainBudgetScreen(
    showDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val budgetViewModel: BudgetViewModel = hiltViewModel()

    val budgetAmount by budgetViewModel.budgetAmount.collectAsState()
    val spentAmount by budgetViewModel.spentAmount.collectAsState()
    val budgetCycle by budgetViewModel.budgetCycle.collectAsState()

    val authViewModel: AuthViewModel = hiltViewModel()
    authViewModel.getAppTheme()
    val appTheme = authViewModel.themeState.collectAsState()

    val progress by remember {
        derivedStateOf {
            if (budgetAmount > 0) spentAmount.toFloat() / budgetAmount.toFloat() else 0f
        }
    }

    LaunchedEffect(budgetCycle, spentAmount) {
        budgetViewModel.getCurrentSpending(budgetCycle)
    }

    Column(
        modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Budget Control", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        CircularProgressBar(
            progress = progress.coerceIn(0f, 1f),
            color = Color.Green,
            backgroundColor = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Budget Cycle: $budgetCycle",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Budget: $budgetAmount",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Spent: ${if (spentAmount < 0) 0.0 else spentAmount}",
            fontSize = 18.sp,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            colors = if (appTheme.value == "light") lightModeColors.customButtonColors else darkModeColors.customButtonColors,
            onClick = {
                showDialog.value = !showDialog.value
            }
        ) {
            Text("Switch Budget Plan")
        }
    }
}


@Composable
fun CircularProgressBar(
    progress: Float, // Progress from 0f to 1f (e.g., 0.75 for 75%)
    modifier: Modifier = Modifier,
    size: Dp = 200.dp, // Increased size
    strokeWidth: Dp = 15.dp, // Adjust stroke width for a balanced look
    color: Color = Color.Blue,
    backgroundColor: Color = Color.LightGray,
    textColor: Color = Color.Black
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size) // Increase size of the circle
    ) {
        // Background Circle
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = backgroundColor, style = Stroke(width = strokeWidth.toPx()))
        }

        // Progress Arc
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        // Percentage Text
        Text(
            text = "${(progress * 100).toInt()}%",
            fontSize = (size.value / 6).sp, // Adjust font size based on the circle size
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

