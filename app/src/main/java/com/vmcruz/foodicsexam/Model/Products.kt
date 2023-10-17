package com.vmcruz.foodicsexam.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Products(
    @PrimaryKey(autoGenerate = true)
    val autoId : Int = 0,
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
