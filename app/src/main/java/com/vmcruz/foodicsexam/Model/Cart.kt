package com.vmcruz.foodicsexam.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = false)
    val itemId : Int,
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "bgColor")
    val bgColor: String
)


