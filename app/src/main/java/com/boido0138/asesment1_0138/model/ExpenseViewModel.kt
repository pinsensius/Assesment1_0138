package com.boido0138.asesment1_0138.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boido0138.asesment1_0138.database.ExpenseDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ExpenseViewModel(dao: ExpenseDao): ViewModel() {
    val data : StateFlow<List<Expense>> = dao.getExpense().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun getExpense(id : Long): Expense?{
        return data.value.find { it.id == id }
    }
}