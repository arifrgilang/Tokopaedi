package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.CartDomainModel
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/26/2021
 */
interface CartRepository {
    suspend fun insert(item: CartDomainModel)
    suspend fun deleteWithId(itemId: Int)
    suspend fun deleteByEmail(userEmail: String)
    fun getCartWithEmail(userEmail: String): Flow<List<CartDomainModel>>
}