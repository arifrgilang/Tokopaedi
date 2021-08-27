package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.ItemDomainModel
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun postItems(items: List<ItemDomainModel>)
    fun getAllItem(): Flow<List<ItemDomainModel>>
    fun getItem(itemId: Int): Flow<ItemDomainModel>
    fun getItemWithCategory(itemCategory: String?): Flow<List<ItemDomainModel>>
}