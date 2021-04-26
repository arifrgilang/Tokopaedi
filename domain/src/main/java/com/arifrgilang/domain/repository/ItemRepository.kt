package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.ItemDomainModel
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun postItems(items: List<ItemDomainModel>)
    suspend fun getAllItem(): List<ItemDomainModel>
    suspend fun getItem(itemId: Int): ItemDomainModel
    suspend fun getItemWithCategory(itemCategory: String?): List<ItemDomainModel>
}