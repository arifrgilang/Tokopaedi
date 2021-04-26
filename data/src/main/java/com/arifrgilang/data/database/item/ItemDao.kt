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
    suspend fun insertAll(items: List<ItemDataModel>)

    @Query("SELECT * FROM ITEM")
    suspend fun getAllItem(): List<ItemDataModel>

    @Query("SELECT * FROM ITEM WHERE id = :itemId")
    suspend fun getItem(itemId: Int): ItemDataModel

    @Query("SELECT * FROM ITEM WHERE ITEM_CATEGORY = :itemCategory")
    suspend fun getItemWithCategory(itemCategory: String?): List<ItemDataModel>
}