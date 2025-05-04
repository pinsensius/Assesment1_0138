package com.boido0138.asesment1_0138.model

import androidx.lifecycle.ViewModel

class IncomeViewModel : ViewModel() {
    val data = listOf(
        Income(
            id = 1, title = "deseruisse", values = 7299, date = "intellegebat"
        ),
        Income(
            id = 2, title = "vehicula", values = 6674, date = "elitr"
        ),
        Income(
            id = 3, title = "veri", values = 4315, date = "odio"
        )
    )

    fun getIncome(id : Long): Income?{
        return data.find { it.id == id }
    }
}