package com.arifrgilang.domain.interactor.promo

import com.arifrgilang.domain.model.PromoDomainModel
import com.arifrgilang.domain.repository.PromoRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
interface GetPromoUseCase {
    suspend fun execute(promoId: Int) : Flow<PromoDomainModel>
}

class GetPromoUseCaseImpl(
    private val promoRepository: PromoRepository
) : GetPromoUseCase {
    override suspend fun execute(promoId: Int): Flow<PromoDomainModel> =
        promoRepository.getPromo(promoId)
}