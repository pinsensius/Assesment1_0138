package com.boido0138.asesment1_0138.navigation

import com.boido0138.asesment1_0138.ui.screen.KEY_ID_EXPENSE
import com.boido0138.asesment1_0138.ui.screen.KEY_ID_INCOME

sealed class Screen ( val route : String ){
    data object Home : Screen("homeScreen")
    data object AddExpense : Screen("addExpenseScreen")
    data object AddIncome : Screen("addIncomeScreen")
    data object IncomeList : Screen("incomeListScreen")
    data object ExpenseList : Screen("expenseListScreen")

    data object EditExpense : Screen("addExpenseScreen/{$KEY_ID_EXPENSE}"){
        fun withId(id : Long) = "addExpenseScreen/$id"
    }

    data object EditIncome : Screen("addIncomeScreen/{$KEY_ID_INCOME}"){
        fun withId(id : Long) = "addIncomeScreen/$id"
    }
}
