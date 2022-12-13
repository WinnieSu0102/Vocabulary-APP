package com.example.inventory

import android.icu.util.ChineseCalendar
import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao):ViewModel() { // 將ItemDao傳送至建構函式

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()

    //新增單字
    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    //在 Vocabulary Detail中顯示詳細資料
    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    //新增單字頁面是否有空
    fun isEntryValid(englishName: String, chineseName: String): Boolean {
        if (englishName.isBlank() || chineseName.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewItemEntry(
        englishName: String,
        chineseName: String,
    ): Item {
        return Item(
            englishName = englishName,
            chineseName = chineseName,
        )
    }

    fun addNewItem(englishName: String, chineseName: String) {
        val newItem = getNewItemEntry(englishName, chineseName)
        insertItem(newItem)
    }

    //刪除單字
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    //修改的值傳回Item
    private fun getUpdatedItemEntry(
        id: Int,
        englishName: String,
        chineseName: String
    ): Item {
        return Item(
            id = id,
            englishName = englishName,
            chineseName = chineseName
        )
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    fun updateItem(
        id: Int,
        englishName: String,
        chineseName: String,
    ) {
        val updatedItem = getUpdatedItemEntry(id, englishName, chineseName)
        updateItem(updatedItem)
    }

}
class InventoryViewModelFactory(private val itemDao: ItemDao):ViewModelProvider.Factory{
    //對InventoryViewModel執行個體化
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
