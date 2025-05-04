package com.boido0138.asesment1_0138.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boido0138.asesment1_0138.database.MoneyTrackerDb
import com.boido0138.asesment1_0138.model.ExpenseViewModel
import com.boido0138.asesment1_0138.model.IncomeViewModel

class ViewModelFactory(
    private val context : Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        val dao = MoneyTrackerDb.getInstance(context)
        return when {
            modelClass.isAssignableFrom(ExpenseViewModel::class.java) -> {
                ExpenseViewModel(dao.expenseDao) as T
            }
            modelClass.isAssignableFrom(IncomeViewModel::class.java) -> {
                IncomeViewModel(dao.incomeDao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}