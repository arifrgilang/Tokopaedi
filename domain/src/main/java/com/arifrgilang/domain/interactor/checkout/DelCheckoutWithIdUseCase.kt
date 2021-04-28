package com.arifrgilang.domain.interactor.checkout

import com.arifrgilang.domain.repository.CheckoutRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface DelCheckoutWithIdUseCase {
    suspend fun execute(checkoutId: Int)
}

class DelCheckoutWithIdUseCaseImpl(
    private val checkoutRepository: CheckoutRepository
) : DelCheckoutWithIdUseCase {
    override suspend fun execute(checkoutId: Int) {
        checkoutRepository.deleteWithId(checkoutId)
    }
}