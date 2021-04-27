package com.arifrgilang.domain.interactor.checkout

import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.domain.repository.CheckoutRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface PostCheckoutUseCase {
    suspend fun execute(item: CheckoutDomainModel)
}

class PostCheckoutUseCaseImpl(
    private val checkoutRepository: CheckoutRepository
): PostCheckoutUseCase {
    override suspend fun execute(item: CheckoutDomainModel) {
        checkoutRepository.postCheckout(item)
    }
}