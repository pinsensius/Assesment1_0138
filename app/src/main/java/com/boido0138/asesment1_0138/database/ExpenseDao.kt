package com.boido0138.asesment1_0138.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.boido0138.asesment1_0138.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Query("SELECT * FROM expense ORDER BY id DESC")
    fun getExpense(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE id = :id")
    suspend fun getExpenseById(id: Long): Expense?

    @Query("DELETE FROM expense WHERE id = :id")
    suspend fun deleteById(id: Long)
}