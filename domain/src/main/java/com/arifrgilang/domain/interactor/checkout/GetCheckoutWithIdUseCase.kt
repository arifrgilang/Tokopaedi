package com.arifrgilang.domain.interactor.checkout

import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.domain.repository.CheckoutRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/27/2021
 */
interface GetCheckoutWithIdUseCase {
    fun execute(checkoutId: Int): Flow<CheckoutDomainModel>
}

class GetCheckoutWithIdUseCaseImpl(
    private val checkoutRepository: CheckoutRepository
): GetCheckoutWithIdUseCase {
    override fun execute(checkoutId: Int): Flow<CheckoutDomainModel> =
        checkoutRepository.getCheckoutWithId(checkoutId)
}