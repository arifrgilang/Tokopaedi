package com.arifrgilang.domain.interactor.cart

import com.arifrgilang.domain.model.CartDomainModel
import com.arifrgilang.domain.repository.CartRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
interface GetCartWithEmail {
    suspend fun execute(userEmail: String): List<CartDomainModel>
}

class GetCartWithEmailImpl(
    private val cartRepository: CartRepository
): GetCartWithEmail {
    override suspend fun execute(userEmail: String): List<CartDomainModel> =
        cartRepository.getCartWithEmail(userEmail)
}