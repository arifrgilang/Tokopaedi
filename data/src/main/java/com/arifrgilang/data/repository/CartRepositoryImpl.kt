package com.arifrgilang.data.repository

import com.arifrgilang.data.database.cart.CartDao
import com.arifrgilang.data.database.cart.CartDataMapper
import com.arifrgilang.domain.model.CartDomainModel
import com.arifrgilang.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform


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

    override fun getCartWithEmail(userEmail: String): Flow<List<CartDomainModel>> =
        cartDao.getCartWithEmail(userEmail).map { items ->
            items.map { cartMapper.mapDataToDomain(it) }
        }
}