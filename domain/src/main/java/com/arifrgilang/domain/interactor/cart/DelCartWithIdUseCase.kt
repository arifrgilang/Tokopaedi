package com.arifrgilang.domain.interactor.cart

import com.arifrgilang.domain.repository.CartRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface DelCartWithIdUseCase {
    suspend fun execute(itemId: Int)
}

class DelCartWithIdUseCaseImpl(
    private val cartRepository: CartRepository
): DelCartWithIdUseCase {
    override suspend fun execute(itemId: Int) {
        cartRepository.deleteWithId(itemId)
    }
}