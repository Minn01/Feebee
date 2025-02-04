package com.example.feebee_android_project_app.exchangeRateScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feebee_android_project_app.ui.theme.Feebee_Android_Project_AppTheme

@Composable
fun ExchangeRateScreen(
    modifier: Modifier
) {
    val exchangeRateViewModel: ExchangeRateViewModel = viewModel()
    val options = listOf("THB", "MMK", "EUR")
    val updatedTime = rememberSaveable { mutableStateOf("updated: 10/20/2005") }

    ConstraintLayout(modifier = modifier.fillMaxSize())  {
        val (exchangeRateConvertCard, infoText, conversionRateCols) = createRefs()
        val startGuideLine = createGuidelineFromStart(0.05f)
        val endGuideLine = createGuidelineFromEnd(0.05f)

        ExchangeRateCard(
            modifier = Modifier.constrainAs(exchangeRateConvertCard) {
                top.linkTo(parent.top, 50.dp)
                start.linkTo(startGuideLine)
                end.linkTo(endGuideLine)
                width = Dimension.fillToConstraints
            }
                .defaultMinSize(minHeight = 185.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .constrainAs(infoText) {
                top.linkTo(exchangeRateConvertCard.bottom, 30.dp)
                start.linkTo(startGuideLine)
                end.linkTo(endGuideLine)
                width = Dimension.fillToConstraints
            }
        ){
            Text(
                text = updatedTime.value,
                fontSize = 20.sp,
                modifier = Modifier
            )

            ElevatedCard(
                modifier = Modifier
            ) {
                Text(
                    "Based: USD",
                    modifier = Modifier.padding(12.dp)
                )
            }
        }


        LazyColumn(
            modifier = Modifier.constrainAs(conversionRateCols) {
                top.linkTo(infoText.bottom, 25.dp)
                start.linkTo(startGuideLine)
                end.linkTo(endGuideLine)
                width = Dimension.fillToConstraints
            }
        ) {
            items(options) { option ->
                ExchangeRateTab(
                    countryCode = option,
                    modifier = Modifier
                )
            }
        }
    }
}

//fun dateParse(dateString: String) {
//
//}

@Preview
@Composable
fun ComponentPreviews() {
    Feebee_Android_Project_AppTheme {
        Scaffold { contentPadding ->
            ExchangeRateScreen(Modifier.padding(contentPadding))
        }
    }
}
