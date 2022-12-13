package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "english")
    val englishName: String,
    @ColumnInfo(name = "chinese")
    val chineseName: String,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean=false
)