package com.vmcruz.foodicsexam.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vmcruz.foodicsexam.Model.Orders

@Dao
interface OrderDao : BaseDao<Orders> {
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(orders : Orders)*/

    @Query("SELECT * FROM order_table ORDER BY orderId DESC")
    suspend fun orderedList() : Orders

}