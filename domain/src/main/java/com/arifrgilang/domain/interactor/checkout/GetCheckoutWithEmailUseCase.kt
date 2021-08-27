package com.arifrgilang.domain.interactor.checkout

import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.domain.repository.CheckoutRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/26/2021
 */
interface GetCheckoutWithEmailUseCase {
    fun execute(userEmail: String): Flow<List<CheckoutDomainModel>>
}

class GetCheckoutWithEmailUseCaseImpl(
    private val checkoutRepository: CheckoutRepository
): GetCheckoutWithEmailUseCase {
    override fun execute(userEmail: String): Flow<List<CheckoutDomainModel>> =
        checkoutRepository.getCheckoutWithEmail(userEmail)
}