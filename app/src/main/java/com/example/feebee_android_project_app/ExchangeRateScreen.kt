package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.feebee_android_project_app.ui.theme.Feebee_Android_Project_AppTheme

@Composable
fun ExchangeRateScreen(
    modifier: Modifier
) {
    val options = listOf("something", "something", "something")
    val updatedTime = rememberSaveable { mutableStateOf("updated: 10/20/2005") }

    ConstraintLayout(modifier = Modifier.fillMaxSize())  {
        val (exchangeRateConvertCard, updateDateText, conversionRateCols) = createRefs()
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

        Text(
            text = updatedTime.value,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(updateDateText) {
                top.linkTo(exchangeRateConvertCard.bottom, 25.dp)
                start.linkTo(startGuideLine)
            }
        )

        LazyColumn(
            modifier = Modifier.constrainAs(conversionRateCols) {
                top.linkTo(updateDateText.bottom, 16.dp)
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

@Composable
fun ExchangeRateTab(
    countryCode: String,
    modifier: Modifier
) {
    ElevatedCard(
        onClick = {},
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(countryCode)
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun ExchangeRateCard(
    modifier: Modifier
) {
    val options = remember { listOf("USD", "MMK", "THB", "EUR") }

    val primaryButtonIsExpanded = rememberSaveable { mutableStateOf(false) }
    val secondaryButtonIsExpanded = rememberSaveable { mutableStateOf(false) }

    val primarySelectedOption = rememberSaveable { mutableStateOf(options[0]) }
    val secondarySelectedOption = rememberSaveable { mutableStateOf(options[0]) }

    val primaryNum = rememberSaveable { mutableDoubleStateOf(0.0) }
    val secondaryNum = rememberSaveable { mutableDoubleStateOf(0.0) }

    ElevatedCard(modifier = modifier) {
        ExchangeRateCardUpperRow (
            primaryButtonIsExpanded = primaryButtonIsExpanded,
            secondaryButtonIsExpanded = secondaryButtonIsExpanded,
            primarySelectedOption = primarySelectedOption,
            secondarySelectedOption = secondarySelectedOption,
            options = options,
            modifier = Modifier
        )

        ConversionTextFieldRow (
            primaryNum = primaryNum,
            secondaryNum = secondaryNum,
            primaryTextFieldValueChanged = {
                // TODO: This part may raise errors so fix
                primaryNum.doubleValue = it.toDouble()
            },
            secondaryTextFieldValueChanged = {
                // TODO: This part may raise errors so fix
                secondaryNum.doubleValue = it.toDouble()
            },
            modifier = Modifier
        )
    }
}

@Composable
fun ExchangeRateCardUpperRow (
    primaryButtonIsExpanded: MutableState<Boolean>,
    secondaryButtonIsExpanded: MutableState<Boolean>,
    primarySelectedOption: MutableState<String>,
    secondarySelectedOption: MutableState<String>,
    options: List<String>,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 25.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DropDownButton(
            expanded = primaryButtonIsExpanded,
            selectedCountry = primarySelectedOption,
            options = options,
            modifier = Modifier
        )

        IconButton(
            onClick = {},
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(R.drawable.double_arrow_black),
                contentDescription = "conversion_icon"
            )
        }

        DropDownButton(
            expanded = secondaryButtonIsExpanded,
            selectedCountry = secondarySelectedOption,
            options = options,
            modifier = Modifier
        )
    }
}

@Composable
fun ConversionTextFieldRow (
    primaryNum: MutableState<Double>,
    secondaryNum: MutableState<Double>,
    primaryTextFieldValueChanged: (String) -> Unit,
    secondaryTextFieldValueChanged: (String) -> Unit,
    modifier: Modifier
) {
    Row (
        modifier = modifier
            .padding(top = 25.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth().wrapContentWidth(),
    ) {
        TextField(
            value = primaryNum.value.toString(),
            onValueChange = primaryTextFieldValueChanged,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(25.dp))

        TextField(
            value = secondaryNum.value.toString(),
            onValueChange = secondaryTextFieldValueChanged,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier.weight(1f),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownButton(
    expanded: MutableState<Boolean>,
    selectedCountry: MutableState<String>,
    options: List<String>,
    modifier: Modifier
) {
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it },
        modifier = modifier
    ) {
        TextField(
            value = selectedCountry.value,
            readOnly = true,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true).size(width = 130.dp, height = 50.dp),
            textStyle = TextStyle(fontSize = 16.sp)
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option
                    ) },

                    onClick = {
                        selectedCountry.value = options[index]
                        expanded.value = false
                    }
                )
            }
        }
    }
}

fun parseCountryCode(countryCode: String): Boolean {
    return false
}

@Preview
@Composable
fun ComponentPreviews() {
    Feebee_Android_Project_AppTheme {
        Scaffold { contentPadding ->
            ExchangeRateScreen(Modifier.padding(contentPadding))
        }
    }
}
