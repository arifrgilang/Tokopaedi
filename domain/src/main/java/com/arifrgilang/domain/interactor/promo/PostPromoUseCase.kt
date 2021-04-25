package com.arifrgilang.domain.interactor.promo

import com.arifrgilang.domain.model.PromoDomainModel
import com.arifrgilang.domain.repository.PromoRepository


/**
 * Created by arifrgilang on 4/25/2021
 */
interface PostPromoUseCase {
    suspend fun execute(promos: List<PromoDomainModel>)
}

class PostPromoUseCaseImpl(
    private val promoRepository: PromoRepository
) : PostPromoUseCase {
    override suspend fun execute(promos: List<PromoDomainModel>) =
        promoRepository.postPromos(promos)
}