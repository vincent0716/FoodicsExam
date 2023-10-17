package com.vmcruz.foodicsexam.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_table")
data class Orders(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "payment")
    val payment: String,

)
{
    @PrimaryKey(autoGenerate = true)
    var orderId: Int?=null
}
