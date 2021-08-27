package com.arifrgilang.domain.interactor.history

import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/26/2021
 */
interface GetHistoryWithEmailUseCase {
    fun execute(userEmail: String): Flow<List<HistoryDomainModel>>
}

class GetHistoryWithEmailUseCaseImpl(
    private val historyRepository: HistoryRepository
): GetHistoryWithEmailUseCase {
    override fun execute(userEmail: String): Flow<List<HistoryDomainModel>> =
        historyRepository.getHistoryWithEmail(userEmail)
}
