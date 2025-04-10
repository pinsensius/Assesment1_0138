package com.boido0138.asesment1_0138.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.boido0138.asesment1_0138.ui.screen.AddExpenseScreen
import com.boido0138.asesment1_0138.ui.screen.AddIncomeScreen
import com.boido0138.asesment1_0138.ui.screen.ExpenseListScreen
import com.boido0138.asesment1_0138.ui.screen.HomeScreen
import com.boido0138.asesment1_0138.ui.screen.IncomeListScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route){
            HomeScreen(navController)
        }

        composable(Screen.AddExpense.route){
            AddExpenseScreen(navController)
        }

        composable(Screen.AddIncome.route){
            AddIncomeScreen(navController)
        }

        composable(Screen.IncomeList.route){
            IncomeListScreen(navController)
        }

        composable(Screen.ExpenseList.route){
            ExpenseListScreen(navController)
        }
    }
}