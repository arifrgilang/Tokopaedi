package com.arifrgilang.domain.interactor.cart

import com.arifrgilang.domain.model.CartDomainModel
import com.arifrgilang.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/26/2021
 */
interface GetCartWithEmail {
    fun execute(userEmail: String): Flow<List<CartDomainModel>>
}

class GetCartWithEmailImpl(
    private val cartRepository: CartRepository
): GetCartWithEmail {
    override fun execute(userEmail: String): Flow<List<CartDomainModel>> =
        cartRepository.getCartWithEmail(userEmail)
}