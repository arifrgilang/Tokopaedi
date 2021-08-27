package com.arifrgilang.data.repository

import com.arifrgilang.data.database.checkout.CheckoutDao
import com.arifrgilang.data.database.checkout.CheckoutDataMapper
import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.domain.repository.CheckoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getCheckoutWithId(checkoutId: Int): Flow<CheckoutDomainModel> =
        checkoutDao.getCheckoutWithId(checkoutId).map {
            checkoutMapper.mapDataToDomain(it)
        }

    override fun getCheckoutWithEmail(userEmail: String): Flow<List<CheckoutDomainModel>> =
        checkoutDao.getCheckoutWithEmail(userEmail).map { items ->
            items.map { checkoutMapper.mapDataToDomain(it) }
        }
}