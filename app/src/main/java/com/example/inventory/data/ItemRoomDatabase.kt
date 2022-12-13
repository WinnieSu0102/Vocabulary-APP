package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() { //可擴充的抽象類別

    abstract fun itemDao():ItemDao //宣告回傳ItemDao的抽象函式

    companion object{ //使用類別名稱當限定詞，建立或取得資料庫
        @Volatile
        private var INSTANCE : ItemRoomDatabase?=null //資料庫初始值為null

        fun getDatabase(context:Context): ItemRoomDatabase{
            return INSTANCE ?: synchronized(this){
                //synchronized表示一次只能執行一個執行緒，確保只初始化一次
                //如果INSTANCE是空值，初始化
                val instance=Room.databaseBuilder( //取得資料庫
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration() // 遷移
                    .build()
                INSTANCE=instance
                return instance
            }
        }

    }
}