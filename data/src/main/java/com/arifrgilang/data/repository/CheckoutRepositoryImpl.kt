package com.arifrgilang.data.repository

import com.arifrgilang.data.database.checkout.CheckoutDao
import com.arifrgilang.data.database.checkout.CheckoutDataMapper
import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.domain.repository.CheckoutRepository

/**
 * Created by arifrgilang on 4/26/2021
 */
class CheckoutRepositoryImpl(
    private val checkoutMapper: CheckoutDataMapper,
    private val checkoutDao: CheckoutDao
) : CheckoutRepository {
    override suspend fun postCheckout(item: CheckoutDomainModel) {
        checkoutDao.insert(checkoutMapper.mapDomainToData(item))
    }

    override suspend fun deleteWithId(checkoutId: Int) {
        checkoutDao.deleteWithId(checkoutId)
    }

    override suspend fun getCheckoutWithId(checkoutId: Int): CheckoutDomainModel =
        checkoutMapper.mapDataToDomain(checkoutDao.getCheckoutWithId(checkoutId))


    override suspend fun getCheckoutWithEmail(userEmail: String): List<CheckoutDomainModel> =
        checkoutDao.getCheckoutWithEmail(userEmail).map { items ->
            checkoutMapper.mapDataToDomain(items)
        }
}