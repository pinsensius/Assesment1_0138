package com.boido0138.asesment1_0138.model

import androidx.lifecycle.ViewModel

class ExpenseViewModel : ViewModel() {
    val data = listOf(
        Expense(
            id = 1,"Burger", values = 1121, tags = "dicant", date = "vitae"
        ),
        Expense(
            id = 2, title = "ad", values = 100, tags = "afs", date = "10/10/10"
        ),
        Expense(
            id = 3, title = "dui", values = 7500, tags = "himenaeos", date = "hendrerit"
        )
    )

    fun getExpense(id : Long): Expense?{
        return data.find { it.id == id }
    }
}