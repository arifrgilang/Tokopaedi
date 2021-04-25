package com.arifrgilang.data.database.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
@Dao
interface ItemDao {
    @Insert
    fun insertAll(items: List<ItemDataModel>)

    @Query("SELECT * FROM ITEM")
    fun getAllItem(): Flow<List<ItemDataModel>>

    @Query("SELECT * FROM ITEM WHERE id = :itemId")
    fun getItem(itemId: Int): Flow<ItemDataModel>

    @Query("SELECT * FROM ITEM WHERE ITEM_CATEGORY = :itemCategory")
    fun getItemWithCategory(itemCategory: String?): Flow<List<ItemDataModel>>
}