package com.boido0138.asesment1_0138.navigation

sealed class Screen ( val route : String ){
    data object Home : Screen("homeScreen")
    data object AddExpense : Screen("addExpenseScreen")
    data object AddIncome : Screen("addIncomeScreen")
    data object IncomeList : Screen("incomeListScreen")
    data object ExpenseList : Screen("expenseListScreen")
}
