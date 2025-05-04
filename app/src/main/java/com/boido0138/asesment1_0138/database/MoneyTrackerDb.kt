package com.boido0138.asesment1_0138.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.boido0138.asesment1_0138.model.Expense
import com.boido0138.asesment1_0138.model.Income

@Database(entities = [Expense::class, Income::class], version = 1, exportSchema = false)
abstract class MoneyTrackerDb : RoomDatabase() {

    abstract val expenseDao: ExpenseDao
    abstract val incomeDao: IncomeDao
    companion object {

        @Volatile
        private var INSTANCE: MoneyTrackerDb? = null

        fun getInstance(context: Context): MoneyTrackerDb {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoneyTrackerDb::class.java,
                        "moneytracker.db"
                    ).build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}