package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.HistoryDomainModel
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/26/2021
 */
interface HistoryRepository {
    suspend fun insert(item: HistoryDomainModel)
    fun getHistoryWithId(historyId: Int): Flow<HistoryDomainModel>
    fun getHistoryWithEmail(userEmail: String): Flow<List<HistoryDomainModel>>
}