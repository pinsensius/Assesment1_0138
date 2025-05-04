package com.boido0138.asesment1_0138.ui.screen

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.boido0138.asesment1_0138.R
import com.boido0138.asesment1_0138.model.ExpenseViewModel
import com.boido0138.asesment1_0138.ui.theme.Asesment1_0138Theme
import com.boido0138.asesment1_0138.util.ViewModelFactory
import java.util.Calendar

const val KEY_ID_EXPENSE = "idExpense"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(navController: NavHostController, id : Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel : ExpenseViewModel = viewModel(factory = factory)

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                title = {
                    if(id == null){
                        Text(
                            text = stringResource(id = R.string.add_expense)
                        )
                    }else{
                        Text(
                            text = stringResource(id = R.string.edit_expense)
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    if(id != null){
                        DeleteAction {
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }

                }
            )
        }
    ) { innerPadding ->
        AddExpenseScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            navController = navController,
            id = id,
            context = context,
            viewModel = viewModel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreenContent(modifier: Modifier = Modifier, navController: NavController, id : Long? = null, context: Context, viewModel: ExpenseViewModel) {

    val dateLabel = stringResource(id = R.string.date_label)
    val tagsLabel = stringResource(id = R.string.tags_label)


    var value by rememberSaveable { mutableStateOf("") }
    var valueError by rememberSaveable { mutableStateOf(false) }

    var title by rememberSaveable { mutableStateOf("") }
    var titleError by rememberSaveable { mutableStateOf(false) }

    var tags by rememberSaveable { mutableStateOf((tagsLabel)) }
    var tagsError by rememberSaveable { mutableStateOf(false) }
    var tagsExpanded by rememberSaveable { mutableStateOf(false) }
    val tagsList = listOf(
        stringResource(R.string.primary),
        stringResource(R.string.secondary),
        stringResource(R.string.tertiary)
    )

    var date by rememberSaveable { mutableStateOf((dateLabel)) }
    var dateError by rememberSaveable { mutableStateOf(false) }
    var dateExpanded by rememberSaveable { mutableStateOf(false) }
    val dateState = rememberDatePickerState()

    LaunchedEffect(Unit) {
        if(id == null) return@LaunchedEffect
        val data = viewModel.getExpense(id) ?: return@LaunchedEffect
        title = data.title
        value = data.values.toString()
        tags = data.tags
        date = data.date
    }


    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.budget),
            contentDescription = "Expense",
            modifier = Modifier.size(120.dp).padding(vertical = 20.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = if(id == null) stringResource(id = R.string.add_expense) else stringResource(id = R.string.edit_expense),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
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
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
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
                valueError = value.toIntOrNull() == null
                tagsError = tags == tagsLabel
                dateError = date == dateLabel

                if (titleError || valueError || tagsError || dateError) {
                    Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if(id == null){
                    viewModel.insert(title,value.toInt(),tags, date)
                    }else{
                        viewModel.update(id,title, value.toInt(),tags,date)
                    }
                navController.popBackStack()
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
fun DeleteAction(delete: () -> Unit){
    var expanded by remember { mutableStateOf(false)}

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.other),
            tint = MaterialTheme.colorScheme.primary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.delete_expense)
                    )
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
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
