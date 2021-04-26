package com.arifrgilang.data.repository

import com.arifrgilang.data.database.history.HistoryDao
import com.arifrgilang.data.database.history.HistoryDataMapper
import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.domain.repository.HistoryRepository


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

    override suspend fun getHistoryWithEmail(userEmail: String): List<HistoryDomainModel> =
        historyDao.getHistoryWithEmail(userEmail).map { items ->
            historyMapper.mapDataToDomain(items)
        }
}