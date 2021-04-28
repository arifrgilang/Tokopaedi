package com.arifrgilang.domain.interactor.promo

import com.arifrgilang.domain.model.PromoDomainModel
import com.arifrgilang.domain.repository.PromoRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
interface GetPromosUseCase {
    suspend fun execute(): List<PromoDomainModel>
}

class GetPromosUseCaseImpl(
    private val promoRepository: PromoRepository
) : GetPromosUseCase {
    override suspend fun execute(): List<PromoDomainModel> =
        promoRepository.getAllPromo()
}