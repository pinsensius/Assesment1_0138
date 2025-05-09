package com.boido0138.asesment1_0138.ui.screen

        import android.content.res.Configuration
        import androidx.compose.foundation.BorderStroke
        import androidx.compose.foundation.Image
        import androidx.compose.foundation.clickable
        import androidx.compose.foundation.layout.Arrangement
        import androidx.compose.foundation.layout.Column
        import androidx.compose.foundation.layout.PaddingValues
        import androidx.compose.foundation.layout.Row
        import androidx.compose.foundation.layout.fillMaxSize
        import androidx.compose.foundation.layout.fillMaxWidth
        import androidx.compose.foundation.layout.padding
        import androidx.compose.foundation.layout.size
        import androidx.compose.foundation.lazy.LazyColumn
        import androidx.compose.foundation.lazy.items
        import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
        import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
        import androidx.compose.foundation.lazy.staggeredgrid.items
        import androidx.compose.material.icons.Icons
        import androidx.compose.material.icons.automirrored.filled.ArrowBack
        import androidx.compose.material3.Card
        import androidx.compose.material3.CardDefaults
        import androidx.compose.material3.DividerDefaults
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
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.layout.ContentScale
        import androidx.compose.ui.platform.LocalContext
        import androidx.compose.ui.res.painterResource
        import androidx.compose.ui.res.stringResource
        import androidx.compose.ui.text.font.FontWeight
        import androidx.compose.ui.text.style.TextOverflow
        import androidx.compose.ui.tooling.preview.Preview
        import androidx.compose.ui.unit.dp
        import androidx.lifecycle.viewmodel.compose.viewModel
        import androidx.navigation.NavHostController
        import androidx.navigation.compose.rememberNavController
        import com.boido0138.asesment1_0138.R
        import com.boido0138.asesment1_0138.model.Expense
        import com.boido0138.asesment1_0138.model.ExpenseViewModel
        import com.boido0138.asesment1_0138.navigation.Screen
        import com.boido0138.asesment1_0138.ui.theme.Asesment1_0138Theme
        import com.boido0138.asesment1_0138.ui.theme.ThemeOption
        import com.boido0138.asesment1_0138.util.SettingsDataStore
        import com.boido0138.asesment1_0138.util.ViewModelFactory
        import kotlinx.coroutines.CoroutineScope
        import kotlinx.coroutines.Dispatchers
        import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

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
                    Text(text = stringResource(id = R.string.expense_list))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataStore.saveLayout(!showList)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if(showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if(showList) R.string.grid_view
                                else R.string.list_view
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ExpenseListScreenContent(
            showList,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
            , navController = navController
        )
    }
}

@Composable
fun ExpenseListScreenContent(showList : Boolean,modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel : ExpenseViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.bankrupt),
                contentDescription = "No Money",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp).padding(vertical = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.empty_expense),
                style = MaterialTheme.typography.titleLarge
            )
        }
    } else {
        if(showList) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier,
            ) {
                items(data) {
                    DataCard(
                        expense = it
                    ) {
                        navController.navigate(Screen.EditExpense.withId(it.id))
                    }
                }
            }
        }else{
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxWidth(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom =  84.dp)
            )  {
                items(data){
                    GridItem(
                        expense = it
                    ) {
                        navController.navigate(Screen.EditExpense.withId(it.id))
                    }
                }
            }
        }
    }
}

@Composable
private fun GridItem(expense: Expense, onClick: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth().clickable {
            onClick()
        },
        border = BorderStroke(0.9.dp, DividerDefaults.color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = expense.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = formatValues(expense.values),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(id = R.string.tags_data, expense.tags),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = stringResource(id = R.string.date_data, expense.date),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun DataCard(expense: Expense, onClick : () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(0.9.dp, DividerDefaults.color)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = expense.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = formatValues(expense.values),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.tags_data, expense.tags),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = stringResource(id = R.string.date_data, expense.date),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ExpenseListScreenPreview(){

    Asesment1_0138Theme (
        theme = ThemeOption.LightTheme.name,
        content = {
            ExpenseListScreen(rememberNavController())
        }
    )

}