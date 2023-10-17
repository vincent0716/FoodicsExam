package com.vmcruz.sampleapps.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vmcruz.foodicsexam.Model.Cart
import com.vmcruz.foodicsexam.Model.Orders
import com.vmcruz.foodicsexam.Model.Products
import com.vmcruz.foodicsexam.Room.CartDao
import com.vmcruz.foodicsexam.Room.OrderDao

@Database(entities = [Orders::class, Cart::class,Products::class], version = 1 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
    abstract fun cartDao(): CartDao

    companion object {
        private const val DB_NAME = "employee.db"
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

}