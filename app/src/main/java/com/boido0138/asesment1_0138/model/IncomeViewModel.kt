package com.boido0138.asesment1_0138.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boido0138.asesment1_0138.database.IncomeDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class IncomeViewModel(dao: IncomeDao) : ViewModel() {
    val data : StateFlow<List<Income>> = dao.getIncome().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun getIncome(id : Long): Income?{
        return data.value.find { it.id == id }
    }
}