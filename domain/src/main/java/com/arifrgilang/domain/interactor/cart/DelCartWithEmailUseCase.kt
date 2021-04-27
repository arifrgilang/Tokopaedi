package com.arifrgilang.domain.interactor.cart

import com.arifrgilang.domain.repository.CartRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface DelCartWithEmailUseCase {
    suspend fun execute(email: String)
}

class DelCartWithEmailUseCaseImpl(
    private val cartRepository: CartRepository
) : DelCartWithEmailUseCase {
    override suspend fun execute(email: String) {
        cartRepository.deleteByEmail(email)
    }
}