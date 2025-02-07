package com.example.feebee_android_project_app.exchangeRateScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/*
    TODO: work on the update date and changes in conversion rate number
 */

@Composable
fun ExchangeRateScreen(
    modifier: Modifier
) {
    val context = LocalContext.current
    val exchangeRateViewModel: ExchangeRateViewModel = viewModel()
    val conversionRates = exchangeRateViewModel.conversionRates.collectAsState()
    val updatedTime = rememberSaveable { mutableStateOf("updated: 10/20/2005") }
    val countryCodes = exchangeRateViewModel.countryCodes.collectAsState()

    val primarySelectedOption = rememberSaveable { mutableStateOf("USD") }
    val secondarySelectedOption = rememberSaveable { mutableStateOf("USD") }

    val primaryNum = rememberSaveable { mutableStateOf("0.0") }
    val secondaryNum = rememberSaveable { mutableStateOf("0.0") }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 30.dp),
    ) {
        item {
            ExchangeRateCard(
                primarySelectedOption = primarySelectedOption,
                secondarySelectedOption = secondarySelectedOption,
                primaryNum = primaryNum,
                secondaryNum = secondaryNum,
                onConvertButtonClicked = {
                    val isValidNum = isValidNum(primaryNum.value)
                    val isValidCountryCode =
                        isValidCountryCode(primarySelectedOption.value, conversionRates.value) && isValidCountryCode(secondarySelectedOption.value, conversionRates.value)

                    if (isValidNum && isValidCountryCode) {
                        val conversionRate =  conversionRates.value[secondarySelectedOption.value]
                        if (conversionRate != null) {
                            secondaryNum.value = (conversionRate * primaryNum.value.toDouble()).toString()
                        }
                    } else if (!isValidNum) {
                        Toast.makeText(context, "Invalid Number Please Try Again", Toast.LENGTH_SHORT).show()
                    } else if (!isValidCountryCode) {
                        Toast.makeText(context, "Invalid Country Code Please Try Again", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .defaultMinSize(minHeight = 250.dp)
                    .padding(bottom = 20.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(
                    text = updatedTime.value,
                    fontSize = 20.sp,
                    modifier = Modifier
                )

                Spacer(Modifier.weight(1f))

                ElevatedCard(
                    modifier = Modifier
                ) {
                    Text(
                        "Based: ${exchangeRateViewModel.basedCountryCode}",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        items(countryCodes.value) { countryCode ->
            conversionRates.value[countryCode]?.let { conversionRate ->
                Log.d("CRE", conversionRate.toString())
                ExchangeRateTab(
                    countryCode = countryCode,
                    conversionRate = conversionRate,
                    modifier = Modifier
                )
            }
        }

        item {
            Box(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)) {
                Text("time of next update: ${exchangeRateViewModel.timeOfNextUpdate.collectAsState().value}")
            }
        }
    }
}

//fun dateParse(dateString: String) {
//
//}

fun isValidCountryCode(countryCode: String, conversionRates: Map<String, Double>): Boolean {
    return conversionRates.containsKey(countryCode)
}

fun isValidNum(num: String): Boolean {
    return try {
        num.toDouble() > 0
    } catch (e: NumberFormatException) {
        false
    }
}

//@Preview
//@Composable
//fun ComponentPreviews() {
//    Feebee_Android_Project_AppTheme {
//        Scaffold { contentPadding ->
//            ExchangeRateScreen(Modifier.padding(contentPadding))
//        }
//    }
//}
