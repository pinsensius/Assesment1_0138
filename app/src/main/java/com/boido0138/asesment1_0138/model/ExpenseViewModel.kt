package com.boido0138.asesment1_0138.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boido0138.asesment1_0138.database.ExpenseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(private val dao: ExpenseDao): ViewModel() {
    val data : StateFlow<List<Expense>> = dao.getExpense().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val totalSum : StateFlow<Int> = dao.getExpenseSum().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    suspend fun getExpense(id : Long): Expense?{
        return dao.getExpenseById(id)
    }

    fun insert(title : String, values : Int, tags : String, date : String){
        val expense = Expense(
            title = title,
            values = values,
            tags = tags,
            date = date
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(expense)
        }
    }

    fun update(id : Long, title : String, values : Int, tags : String, date : String){
        val expense = Expense(
            id = id,
            title = title,
            values = values,
            tags = tags,
            date = date
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(expense)
        }
    }

    fun delete(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}