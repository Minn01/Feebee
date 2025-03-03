package com.example.feebee_android_project_app.homeScreens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.feebee_android_project_app.data.Transaction
import com.example.feebee_android_project_app.data.lightModeColors
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.format.DateTimeFormatter

enum class GroupBy { MONTH, YEAR }

@Composable
fun TransactionBarChart(
    transactions: List<Transaction>,
    groupBy: GroupBy, // Month or Year
    height: Dp = 200.dp,
    modifier: Modifier
) {
    val groupedData = groupTransactionsBy(transactions, groupBy)

    val labels = groupedData.keys.toList() // Get month/year labels
    val entries = groupedData.values.mapIndexed { index, amount ->
        BarEntry(index.toFloat(), amount.toFloat()) // X = index, Y = amount
    }

    val barColors = intArrayOf(
        lightModeColors.barColor.toArgb(),
        lightModeColors.barColor.toArgb(),
        lightModeColors.barColor.toArgb()
    )

    ElevatedCard(modifier = modifier) {
        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    description.isEnabled = false
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    axisRight.isEnabled = false
                    axisLeft.granularity = 1f
                    xAxis.granularity = 1f
                    xAxis.valueFormatter = DateAxisFormatter(labels) // Apply custom labels
                    legend.isEnabled = false
                }
            },
            update = { chart ->
                val dataSet = BarDataSet(entries, "Transactions").apply {
                    color = lightModeColors.barColor.toArgb()
                    valueTextColor = Color.Black.toArgb()
                }
                // Set the bar width here
                val barData = BarData(dataSet).apply {
                    barWidth = 0.3f
                }
                chart.data = barData
                chart.invalidate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}

class DateAxisFormatter(private val labels: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt()
        return if (index in labels.indices) labels[index] else ""
    }
}

// Function to group transactions by month or year
fun groupTransactionsBy(
    transactions: List<Transaction>,
    groupBy: GroupBy // Custom enum (MONTH or YEAR)
): Map<String, Double> {
    return transactions.groupBy { transaction ->
        when (groupBy) {
            GroupBy.MONTH -> transaction.createdDate.format(DateTimeFormatter.ofPattern("MMM yyyy"))
            GroupBy.YEAR -> transaction.createdDate.year.toString()
        }
    }.mapValues { entry -> entry.value.sumOf { it.transactionAmount } }
}

