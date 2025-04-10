package com.boido0138.asesment1_0138.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.boido0138.asesment1_0138.R
import com.boido0138.asesment1_0138.navigation.Screen
import com.boido0138.asesment1_0138.ui.theme.Asesment1_0138Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    Box {
                        IconButton(
                            onClick = { menuExpanded = !menuExpanded }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.List,
                                contentDescription = "List",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Income List") },
                                onClick = {
                                    menuExpanded = false
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Expense List") },
                                onClick = {
                                    menuExpanded = false
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            navController = navController
        )
    }
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier,navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.expense_total, "Rp. 100000"),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = stringResource(id = R.string.income_total, "Rp. 2500000"),
            style = MaterialTheme.typography.headlineSmall
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
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomeScreenPreview(){
    Asesment1_0138Theme {
        HomeScreen(rememberNavController())
    }
}