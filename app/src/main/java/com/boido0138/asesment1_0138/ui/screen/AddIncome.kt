package com.boido0138.asesment1_0138.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.boido0138.asesment1_0138.R
import com.boido0138.asesment1_0138.model.Income
import com.boido0138.asesment1_0138.model.IncomeList
import com.boido0138.asesment1_0138.ui.theme.Asesment1_0138Theme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncomeScreen(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        AddIncomeScreenContent(
            Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncomeScreenContent(modifier: Modifier = Modifier, navController: NavController) {
    val dateLabel = stringResource(id = R.string.date_label)
    IncomeList.tempIncome = Income("", 0, dateLabel)

    var value by rememberSaveable { mutableStateOf(IncomeList.tempIncome.values.toString()) }
    var valueError by rememberSaveable { mutableStateOf(false) }

    var title by rememberSaveable { mutableStateOf(IncomeList.tempIncome.title) }
    var titleError by rememberSaveable { mutableStateOf(false) }

    var date by rememberSaveable { mutableStateOf(IncomeList.tempIncome.date) }
    var dateError by rememberSaveable { mutableStateOf(false) }
    var dateButtonExpanded by rememberSaveable { mutableStateOf(false) }
    val dateState = rememberDatePickerState()

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.growth),
            contentDescription = "Income",
            modifier = Modifier.size(120.dp).padding(vertical = 20.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = stringResource(id = R.string.add_income),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.form_intro),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                IncomeList.tempIncome = Income(title, value.toIntOrNull() ?: 0, date)
            },
            label = { Text(stringResource(id = R.string.title_label)) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            trailingIcon = { IconSelector(titleError) },
            singleLine = true,
            isError = titleError,
            modifier = Modifier.fillMaxWidth()
        )
        ErrorNotification(titleError, stringResource(id = R.string.title_error))

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = value,
            onValueChange = {
                value = it
                IncomeList.tempIncome = Income(title, value.toIntOrNull() ?: 0, date)
            },
            label = { Text(stringResource(id = R.string.money_label)) },
            leadingIcon = { Text(stringResource(id = R.string.rp)) },
            trailingIcon = { IconSelector(valueError) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            singleLine = true,
            isError = valueError,
            modifier = Modifier.fillMaxWidth()
        )
        ErrorNotification(valueError, stringResource(id =R.string.values_error))

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dateButtonExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = date.ifEmpty { stringResource(R.string.date_label) })
                Icon(Icons.Outlined.DateRange, contentDescription = "Date", modifier = Modifier.padding(start = 8.dp))
            }

            if (dateButtonExpanded) {
                DatePickerDialog(
                    onDismissRequest = { dateButtonExpanded = false },
                    confirmButton = {
                        Button(onClick = {
                            dateState.selectedDateMillis?.let { millis ->
                                val cal = Calendar.getInstance().apply { timeInMillis = millis }
                                date = "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"
                                IncomeList.tempIncome = Income(title, value.toIntOrNull() ?: 0, date)
                            }
                            dateButtonExpanded = false
                        }) {
                            Text(stringResource(id = R.string.confirm))
                        }
                    }
                ) {
                    DatePicker(state = dateState)
                }
            }
        }
        ErrorNotification(dateError, stringResource(id =R.string.date_error))

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                titleError = title.isBlank()
                valueError = value.isBlank() || value.toIntOrNull() == null || value.toInt() == 0
                dateError = date.isBlank() || date == dateLabel

                if (!titleError && !valueError && !dateError) {
                    IncomeList.addToIncomeList(Income(title, value.toInt(), date))
                    IncomeList.tempIncome = Income("", 0,dateLabel)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.confirm))
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddIncomeScreenPreview(){
    Asesment1_0138Theme {
        AddIncomeScreen(rememberNavController())
    }
}
