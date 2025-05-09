package com.boido0138.asesment1_0138.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.boido0138.asesment1_0138.R
import com.boido0138.asesment1_0138.model.ExpenseViewModel
import com.boido0138.asesment1_0138.model.IncomeViewModel
import com.boido0138.asesment1_0138.navigation.Screen
import com.boido0138.asesment1_0138.ui.theme.Asesment1_0138Theme
import com.boido0138.asesment1_0138.ui.theme.ThemeOption
import com.boido0138.asesment1_0138.util.SettingsDataStore
import com.boido0138.asesment1_0138.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val expenseViewModel : ExpenseViewModel = viewModel(factory = factory)
    val incomeViewModel : IncomeViewModel = viewModel(factory = factory)
    var menuExpanded by remember { mutableStateOf(false) }
    val dataStore = SettingsDataStore(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    Box {
                        IconButton(
                            onClick = { menuExpanded = !menuExpanded }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.List,
                                contentDescription = "List",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(id = R.string.expense_list)) },
                                onClick = {
                                    navController.navigate(Screen.ExpenseList.route)
                                    menuExpanded = false
                                }
                            )

                            DropdownMenuItem(
                                text = { Text(stringResource(id = R.string.income_list)) },
                                onClick = {
                                    navController.navigate(Screen.IncomeList.route)
                                    menuExpanded = false
                                }
                            )
                        }
                    }

                    ThemeSelector(dataStore = dataStore)
                }
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            navController = navController,
            context = context,
            expenseViewModel = expenseViewModel,
            incomeViewModel = incomeViewModel
        )
    }
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier,navController: NavController, context : Context, expenseViewModel: ExpenseViewModel, incomeViewModel: IncomeViewModel) {
    val totalExpense by expenseViewModel.totalSum.collectAsState()
    val totalIncome by incomeViewModel.totalSum.collectAsState()

    val totalMoney = totalIncome - totalExpense


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Image(
            painter = painterResource(id = R.drawable.money_bag),
            contentDescription = "Money Bag",
            modifier = Modifier.size(180.dp).padding(vertical = 20.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = stringResource(id = R.string.expense_total, formatSumValues(totalExpense)),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Text(
            text = stringResource(id = R.string.income_total, formatSumValues(totalIncome)),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )


        Text(
            text = stringResource(id = R.string.money_total, formatSumValues(totalMoney)),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 12.dp, bottom = 32.dp)

        )

        Button(
            onClick = {
                navController.navigate(Screen.AddExpense.route)
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_expense),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(Screen.AddIncome.route)
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_income),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = {
                shareKeuangan(
                    context = context,
                    message = context.getString(R.string.share_template, formatSumValues(totalExpense),formatSumValues(totalIncome), formatSumValues(totalMoney))
                )
            },
            modifier =  Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.share),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun formatSumValues(total : Int) : String{
    val jenisUang = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return jenisUang.format(total)
}

private fun shareKeuangan(context: Context, message: String){
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }

    val chooser = Intent.createChooser(shareIntent, context.getString(R.string.share_title))

    if(shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(chooser)
    }
}

@Composable
fun ThemeSelector(dataStore: SettingsDataStore){

    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            expanded = true
        },
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.other),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = ThemeOption.LightTheme.name)
                },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveTheme(ThemeOption.LightTheme.name)
                    }
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = ThemeOption.DarkTheme.name)
                },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveTheme(ThemeOption.DarkTheme.name)
                    }
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = ThemeOption.MediumContrastLightTheme.name)
                },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveTheme(ThemeOption.MediumContrastLightTheme.name)
                    }
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = ThemeOption.HighContrastLightTheme.name)
                },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveTheme(ThemeOption.HighContrastLightTheme.name)
                    }
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = ThemeOption.MediumContrastDarkTheme.name)
                },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveTheme(ThemeOption.MediumContrastDarkTheme.name)
                    }
                }
            )

            DropdownMenuItem(
                text = {
                    Text(text = ThemeOption.HighContrastDarkTheme.name)
                },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.saveTheme(ThemeOption.HighContrastDarkTheme.name)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomeScreenPreview(){

    Asesment1_0138Theme (
        theme = ThemeOption.LightTheme.name,
        content = {
            HomeScreen(rememberNavController())
        }
    )

}