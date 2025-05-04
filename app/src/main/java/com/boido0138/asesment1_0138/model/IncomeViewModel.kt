package com.boido0138.asesment1_0138.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boido0138.asesment1_0138.database.IncomeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IncomeViewModel(private val dao: IncomeDao) : ViewModel() {
    val data : StateFlow<List<Income>> = dao.getIncome().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun getIncome(id : Long): Income?{
        return null
    }

    fun insert(title : String, values : Int, date : String){
        val income = Income(
            title = title,
            values = values,
            date = date
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(income)
        }
    }
}