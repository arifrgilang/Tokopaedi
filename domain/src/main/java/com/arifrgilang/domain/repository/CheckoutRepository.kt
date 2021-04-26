package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.CheckoutDomainModel


/**
 * Created by arifrgilang on 4/26/2021
 */
interface CheckoutRepository {
    suspend fun postCheckout(item: CheckoutDomainModel)
    suspend fun deleteWithId(checkoutId: Int)
    suspend fun getCheckoutWithEmail(userEmail: String): List<CheckoutDomainModel>
}