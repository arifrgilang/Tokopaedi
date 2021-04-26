package com.arifrgilang.data.repository

import android.util.Log
import com.arifrgilang.data.database.item.ItemDao
import com.arifrgilang.data.database.item.ItemDataMapper
import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class ItemRepositoryImpl(
    private val itemMapper: ItemDataMapper,
    private val itemDao: ItemDao
) : ItemRepository {
    override suspend fun postItems(items: List<ItemDomainModel>) {
        itemDao.insertAll(
            items.map{
                itemMapper.mapDomainToData(it)
            }
        )
    }

    override suspend fun getAllItem(): List<ItemDomainModel> =
        itemDao.getAllItem().map { list ->
            itemMapper.mapDataToDomain(list)
        }

    override suspend fun getItem(itemId: Int): ItemDomainModel =
        itemMapper.mapDataToDomain(itemDao.getItem(itemId))

    override suspend fun getItemWithCategory(itemCategory: String?): List<ItemDomainModel> =
        itemDao.getItemWithCategory(itemCategory).map { items ->
            itemMapper.mapDataToDomain(items)
        }
}