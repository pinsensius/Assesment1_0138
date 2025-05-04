package com.boido0138.asesment1_0138.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.boido0138.asesment1_0138.model.Income
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {

    @Insert
    suspend fun insert(income: Income)

    @Update
    suspend fun update(income: Income)

    @Query("SELECT * FROM income ORDER BY id DESC")
    fun getIncome(): Flow<List<Income>>

    @Query("SELECT * FROM income WHERE id = :id")
    suspend fun getIncomeById(id: Long): Income?

}