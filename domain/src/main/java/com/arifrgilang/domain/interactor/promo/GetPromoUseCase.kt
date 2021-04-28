package com.arifrgilang.domain.interactor.promo

import com.arifrgilang.domain.model.PromoDomainModel
import com.arifrgilang.domain.repository.PromoRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
interface GetPromoUseCase {
    suspend fun execute(promoId: Int) : PromoDomainModel
}

class GetPromoUseCaseImpl(
    private val promoRepository: PromoRepository
) : GetPromoUseCase {
    override suspend fun execute(promoId: Int): PromoDomainModel =
        promoRepository.getPromo(promoId)
}