package com.arifrgilang.domain.interactor.history

import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.domain.repository.HistoryRepository


/**
 * Created by arifrgilang on 4/27/2021
 */
interface GetHistoryWithIdUseCase {
    suspend fun execute(historyId: Int): HistoryDomainModel
}

class GetHistoryWithIdUseCaseImpl(
    private val historyRepository: HistoryRepository
): GetHistoryWithIdUseCase {
    override suspend fun execute(historyId: Int): HistoryDomainModel =
        historyRepository.getHistoryWithId(historyId)
}