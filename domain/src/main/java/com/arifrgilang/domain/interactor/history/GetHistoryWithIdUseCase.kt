package com.arifrgilang.domain.interactor.history

import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/27/2021
 */
interface GetHistoryWithIdUseCase {
    fun execute(historyId: Int): Flow<HistoryDomainModel>
}

class GetHistoryWithIdUseCaseImpl(
    private val historyRepository: HistoryRepository
): GetHistoryWithIdUseCase {
    override fun execute(historyId: Int): Flow<HistoryDomainModel> =
        historyRepository.getHistoryWithId(historyId)
}