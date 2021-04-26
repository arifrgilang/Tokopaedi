package com.arifrgilang.data.database.history

import com.arifrgilang.data.database.cart.CartDataMapper
import com.arifrgilang.data.database.checkout.CheckoutDataMapper
import com.arifrgilang.data.database.checkout.CheckoutDataModel
import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.domain.model.HistoryDomainModel


/**
 * Created by arifrgilang on 4/26/2021
 */
interface HistoryDataMapper {
    fun mapDomainToData(data: HistoryDomainModel): HistoryDataModel
    fun mapDataToDomain(data: HistoryDataModel): HistoryDomainModel
}

class HistoryDataMapperImpl(
    private val cartDataMapper: CartDataMapper
): HistoryDataMapper {
    override fun mapDomainToData(data: HistoryDomainModel): HistoryDataModel =
        HistoryDataModel(
            data.id,
            data.userEmail,
            data.items.map { cartDataMapper.mapDomainToData(it) }
        )

    override fun mapDataToDomain(data: HistoryDataModel): HistoryDomainModel =
        HistoryDomainModel(
            data.id,
            data.userEmail,
            data.items.map { cartDataMapper.mapDataToDomain(it) }
        )

}