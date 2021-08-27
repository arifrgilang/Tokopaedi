package com.arifrgilang.data.repository

import com.arifrgilang.data.database.history.HistoryDao
import com.arifrgilang.data.database.history.HistoryDataMapper
import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Created by arifrgilang on 4/26/2021
 */
class HistoryRepositoryImpl(
    private val historyMapper: HistoryDataMapper,
    private val historyDao: HistoryDao
) : HistoryRepository {
    override suspend fun insert(item: HistoryDomainModel) {
        historyDao.insert(historyMapper.mapDomainToData(item))
    }

    override fun getHistoryWithId(historyId: Int): Flow<HistoryDomainModel> =
        historyDao.getHistoryWithId(historyId).map { historyMapper.mapDataToDomain(it) }

    override fun getHistoryWithEmail(userEmail: String): Flow<List<HistoryDomainModel>> =
        historyDao.getHistoryWithEmail(userEmail).map { items ->
            items.map { historyMapper.mapDataToDomain(it) }
        }
}