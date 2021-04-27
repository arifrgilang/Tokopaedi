package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.HistoryDomainModel


/**
 * Created by arifrgilang on 4/26/2021
 */
interface HistoryRepository {
    suspend fun insert(item: HistoryDomainModel)
    suspend fun getHistoryWithId(historyId: Int): HistoryDomainModel
    suspend fun getHistoryWithEmail(userEmail: String): List<HistoryDomainModel>
}