package com.arifrgilang.domain.interactor.history

import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.domain.repository.HistoryRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface GetHistoryWithEmailUseCase {
    suspend fun execute(userEmail: String): List<HistoryDomainModel>
}

class GetHistoryWithEmailUseCaseImpl(
    private val historyRepository: HistoryRepository
): GetHistoryWithEmailUseCase {
    override suspend fun execute(userEmail: String): List<HistoryDomainModel> =
        historyRepository.getHistoryWithEmail(userEmail)
}
