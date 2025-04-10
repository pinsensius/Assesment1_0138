package com.boido0138.asesment1_0138.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.boido0138.asesment1_0138.ui.theme.Asesment1_0138Theme
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        AddExpenseScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreenContent(modifier: Modifier = Modifier, navController: NavController) {
    var value by rememberSaveable { mutableStateOf("") }
    var valueError by rememberSaveable { mutableStateOf(false) }

    var title by rememberSaveable { mutableStateOf("")}
    var titleError by rememberSaveable { mutableStateOf(false) }

    var tags by rememberSaveable { mutableStateOf("") }
    var tagsError by rememberSaveable { mutableStateOf(false) }
    var tagsExpanded by rememberSaveable { mutableStateOf(false) }
    val tagsList = listOf(
        stringResource(R.string.primary),
        stringResource(R.string.secondary),
        stringResource(R.string.tertiary)
    )

    var date by rememberSaveable { mutableStateOf("") }
    var dateError by rememberSaveable { mutableStateOf(false) }
    var dateExpanded by rememberSaveable { mutableStateOf(false) }
    val dateState = rememberDatePickerState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.add_expense),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = stringResource( id = R.string.form_intro),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = { Text(stringResource(R.string.title_label)) },
            trailingIcon = { IconSelector(titleError) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            isError = titleError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        ErrorNotification(titleError, stringResource( id = R.string.title_error))

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = value,
            onValueChange = {
                value = it
            },
            label = { Text(stringResource(R.string.money_label)) },
            leadingIcon = { Text(stringResource(R.string.rp)) },
            trailingIcon = { IconSelector(valueError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            isError = valueError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        ErrorNotification(valueError, stringResource( id = R.string.values_error))

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { tagsExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(tags.ifEmpty { stringResource(R.string.tags_label) })
                Icon(Icons.Outlined.ArrowDropDown, contentDescription = "Tags")
            }

            DropdownMenu(
                expanded = tagsExpanded,
                onDismissRequest = { tagsExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                tagsList.forEach { tag ->
                    DropdownMenuItem(
                        text = { Text(tag) },
                        onClick = {
                            tags = tag
                            tagsExpanded = false
                        }
                    )
                }
            }
        }
        ErrorNotification(tagsError, stringResource( id = R.string.tags_error))

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { dateExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(date.ifEmpty { stringResource(R.string.date_label) })
                Icon(Icons.Outlined.DateRange, contentDescription = "Date", modifier = Modifier.padding(start = 6.dp))
            }

            if (dateExpanded) {
                DatePickerDialog(
                    onDismissRequest = { dateExpanded = false },
                    confirmButton = {
                        Button(onClick = {
                            dateState.selectedDateMillis?.let { millis ->
                                val cal = Calendar.getInstance().apply { timeInMillis = millis }
                                date = "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.YEAR)}"
                            }
                            dateExpanded = false
                        }) {
                            Text(stringResource(R.string.confirm))
                        }
                    }
                ) {
                    DatePicker(state = dateState)
                }
            }
        }
        ErrorNotification(dateError, stringResource( id = R.string.date_error))

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                titleError = title.isBlank()
                valueError = value.isBlank() || value.toIntOrNull() == null
                tagsError = tags.isBlank()
                dateError = date.isBlank()

                if (!titleError && !valueError && !tagsError && !dateError) {
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.confirm))
        }
    }
}

@Composable
fun ErrorNotification(isError: Boolean, message: String) {
    if (isError) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun IconSelector(isError: Boolean, content: @Composable () -> Unit = {}) {
    if (isError) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error
        )
    } else {
        content()
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddExpenseScreenPreview() {
    Asesment1_0138Theme {
        AddExpenseScreen(rememberNavController())
    }
}
