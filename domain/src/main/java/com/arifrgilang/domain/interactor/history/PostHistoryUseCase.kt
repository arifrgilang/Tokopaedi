package com.arifrgilang.domain.interactor.history

import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.domain.repository.HistoryRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface PostHistoryUseCase {
    suspend fun execute(item: HistoryDomainModel)
}

class PostHistoryUseCaseImpl(
    private val historyRepository: HistoryRepository
): PostHistoryUseCase {
    override suspend fun execute(item: HistoryDomainModel) {
        historyRepository.insert(item)
    }
}