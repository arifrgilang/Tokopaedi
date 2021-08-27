package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.CheckoutDomainModel
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/26/2021
 */
interface CheckoutRepository {
    suspend fun postCheckout(item: CheckoutDomainModel)
    suspend fun deleteWithId(checkoutId: Int)
    fun getCheckoutWithId(checkoutId: Int): Flow<CheckoutDomainModel>
    fun getCheckoutWithEmail(userEmail: String): Flow<List<CheckoutDomainModel>>
}