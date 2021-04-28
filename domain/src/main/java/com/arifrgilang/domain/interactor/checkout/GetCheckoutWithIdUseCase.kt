package com.arifrgilang.domain.interactor.checkout

import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.domain.repository.CheckoutRepository


/**
 * Created by arifrgilang on 4/27/2021
 */
interface GetCheckoutWithIdUseCase {
    suspend fun execute(checkoutId: Int): CheckoutDomainModel
}

class GetCheckoutWithIdUseCaseImpl(
    private val checkoutRepository: CheckoutRepository
): GetCheckoutWithIdUseCase {
    override suspend fun execute(checkoutId: Int): CheckoutDomainModel =
        checkoutRepository.getCheckoutWithId(checkoutId)
}