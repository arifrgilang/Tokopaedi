package com.arifrgilang.domain.interactor.checkout

import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.domain.repository.CheckoutRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface GetCheckoutWithEmailUseCase {
    suspend fun execute(userEmail: String): List<CheckoutDomainModel>
}

class GetCheckoutWithEmailUseCaseImpl(
    private val checkoutRepository: CheckoutRepository
): GetCheckoutWithEmailUseCase {
    override suspend fun execute(userEmail: String): List<CheckoutDomainModel> =
        checkoutRepository.getCheckoutWithEmail(userEmail)
}