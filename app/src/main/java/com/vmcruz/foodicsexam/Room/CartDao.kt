package com.vmcruz.foodicsexam.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.vmcruz.foodicsexam.Model.Cart
import androidx.room.Query
import androidx.room.Delete


@Dao
interface CartDao : BaseDao<Cart>  {

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addtoCart(cart : Cart)

    @Delete
    suspend fun deleteItem(cart:Cart)*/

    @Query("SELECT * FROM cart_table")
    suspend fun itemInCartList() : List<Cart>

    @Query("SELECT * FROM cart_table WHERE id=:id")
    suspend fun isExisting(id:String) : List<Cart>

    @Query("DELETE FROM cart_table")
    suspend fun deleteCart()
}