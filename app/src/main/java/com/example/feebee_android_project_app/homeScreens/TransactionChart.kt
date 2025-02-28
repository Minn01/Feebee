package com.example.feebee_android_project_app.homeScreens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.feebee_android_project_app.data.Transaction


@Composable
fun TransactionLineChart(transactionList: List<Transaction>) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false // Remove chart description
                setTouchEnabled(true)
                setPinchZoom(true)
                legend.isEnabled = false // Hide legend
                xAxis.position = XAxis.XAxisPosition.BOTTOM // X-axis at bottom
                xAxis.granularity = 1f // Minimum interval
                axisRight.isEnabled = false // Disable right Y-axis
            }
        },
        update = { chart ->
            val entries = transactionList.mapIndexed { index, transaction ->
                Entry(index.toFloat(), transaction.transactionAmount.toFloat())
            }

            val dataSet = LineDataSet(entries, "Transactions").apply {
                color = Color.Blue.toArgb()
                setDrawCircles(true)
                setDrawValues(false)
                lineWidth = 2f
                valueTextSize = 12f
            }

            chart.data = LineData(dataSet)
            chart.invalidate() // Refresh chart
        }
    )
}
