package com.arifrgilang.data.database.checkout

import com.arifrgilang.data.database.cart.CartDataMapper
import com.arifrgilang.data.database.cart.CartDataModel
import com.arifrgilang.domain.model.CheckoutDomainModel


/**
 * Created by arifrgilang on 4/26/2021
 */
interface CheckoutDataMapper {
    fun mapDomainToData(data: CheckoutDomainModel): CheckoutDataModel
    fun mapDataToDomain(data: CheckoutDataModel): CheckoutDomainModel
}

class CheckoutDataMapperImpl(
    private val cartDataMapper: CartDataMapper
): CheckoutDataMapper {
    override fun mapDomainToData(data: CheckoutDomainModel): CheckoutDataModel =
        CheckoutDataModel(
            data.id,
            data.userEmail,
            data.items.map { cartDataMapper.mapDomainToData(it) }
        )

    override fun mapDataToDomain(data: CheckoutDataModel): CheckoutDomainModel =
        CheckoutDomainModel(
            data.id,
            data.userEmail,
            data.items.map { cartDataMapper.mapDataToDomain(it) }
        )
}