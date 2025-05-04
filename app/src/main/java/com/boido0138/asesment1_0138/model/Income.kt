package com.boido0138.asesment1_0138.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income")
data class Income(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val title : String,
    val values : Int,
    val date : String
)
