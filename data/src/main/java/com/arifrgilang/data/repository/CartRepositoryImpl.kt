package com.arifrgilang.data.repository

import com.arifrgilang.data.database.cart.CartDao
import com.arifrgilang.data.database.cart.CartDataMapper
import com.arifrgilang.domain.model.CartDomainModel
import com.arifrgilang.domain.repository.CartRepository


/**
 * Created by arifrgilang on 4/26/2021
 */
class CartRepositoryImpl(
    private val cartMapper: CartDataMapper,
    private val cartDao: CartDao
) : CartRepository {
    override suspend fun insert(item: CartDomainModel) {
        cartDao.insert(cartMapper.mapDomainToData(item))
    }

    override suspend fun deleteWithId(itemId: Int) {
        cartDao.deleteById(itemId)
    }

    override suspend fun deleteByEmail(userEmail: String) {
        cartDao.deleteByEmail(userEmail)
    }

    override suspend fun getCartWithEmail(userEmail: String): List<CartDomainModel> =
        cartDao.getCartWithEmail(userEmail).map { items ->
            cartMapper.mapDataToDomain(items)
        }
}