package com.arifrgilang.domain.interactor.cart

import com.arifrgilang.domain.model.CartDomainModel
import com.arifrgilang.domain.repository.CartRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface PostCartUseCase {
    suspend fun execute(item: CartDomainModel)
}

class PostCartUseCaseImpl(
    private val cartRepository: CartRepository
): PostCartUseCase {
    override suspend fun execute(item: CartDomainModel) =
        cartRepository.insert(item)
}