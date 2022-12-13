package com.example.inventory.data

import android.database.Cursor
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)//如果主鍵已存在，忽略新增
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * from item WHERE id= :id")
    fun getItem(id:Int): Flow<Item> //Flow:持續回傳

    @Query("SELECT * from item ORDER BY english ASC")
    fun getItems():Flow<List<Item>>

    @Query("SELECT * from item ORDER BY english ASC")
    fun getItemTest():Cursor?

}