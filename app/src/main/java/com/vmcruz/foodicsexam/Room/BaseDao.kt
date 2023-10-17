package com.vmcruz.foodicsexam.Room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

import androidx.room.Update


interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)

    @Update
    suspend fun update(entity: T)

    @Delete
    suspend fun delete(entity: T)
}