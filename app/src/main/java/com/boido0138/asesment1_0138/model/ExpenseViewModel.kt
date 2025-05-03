package com.boido0138.asesment1_0138.model

import androidx.lifecycle.ViewModel

class ExpenseViewModel : ViewModel() {
    val data = listOf(
        Expense(
            "Burger", values = 1121, tags = "dicant", date = "vitae"
        ),
        Expense(
            title = "ad", values = 100, tags = "afs", date = "10/10/10"
        ),
        Expense(
            title = "dui", values = 7500, tags = "himenaeos", date = "hendrerit"
        )
    )
}