package com.example.inventory

import android.content.ClipData
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE
import android.graphics.ColorSpace.match
import android.media.tv.TvContract.AUTHORITY
import android.net.Uri
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.room.Query
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import com.example.inventory.data.ItemRoomDatabase
import java.net.URI
import java.util.regex.Matcher

class ItemContentProvider : ContentProvider() {
    private val sUriMatcher=UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.inventory","item",2)
    }

    private lateinit var appDatabase:ItemRoomDatabase
    private var itemDao: ItemDao?= null

    override fun onCreate(): Boolean {
        appDatabase=ItemRoomDatabase.getDatabase(context!!)
        itemDao=appDatabase.itemDao()
        return true
    }

    override fun query(
        uri:Uri, projection:Array<String>?,selection:String?,selectionArgs:Array<String>?,sortOrder:String?
    ): Cursor?{
        return itemDao?.getItemTest()
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArg: Array<String>?
    ): Int {
        return appDatabase.openHelper.writableDatabase.update("item",CONFLICT_IGNORE,values,selection,selectionArg)
    }

    override fun delete(
        uri: Uri, selection: String?, selectionArg: Array<String>?
    ): Int {
        return appDatabase.openHelper.writableDatabase.delete("item",selection,selectionArg)
    }

    override fun insert(
        uri: Uri, values: ContentValues?
    ): Uri? {
        return Uri.parse("content://"+AUTHORITY+"item"+values)
    }

    override fun getType(
        uri: Uri
    ): String? {
        return "vnd.android.cursor.dir/vnd.com.example.inventory.item"
    }
}