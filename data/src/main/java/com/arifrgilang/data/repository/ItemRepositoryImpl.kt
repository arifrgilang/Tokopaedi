package com.arifrgilang.data.repository

import com.arifrgilang.data.database.item.ItemDao
import com.arifrgilang.data.database.item.ItemDataMapper
import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getAllItem(): Flow<List<ItemDomainModel>> =
        itemDao.getAllItem().map { list ->
            list.map { itemMapper.mapDataToDomain(it) }
        }

    override fun getItem(itemId: Int): Flow<ItemDomainModel> =
        itemDao.getItem(itemId).map { itemMapper.mapDataToDomain(it) }

    override fun getItemWithCategory(itemCategory: String?): Flow<List<ItemDomainModel>> =
        itemDao.getItemWithCategory(itemCategory).map { items ->
            items.map { itemMapper.mapDataToDomain(it) }
        }
}