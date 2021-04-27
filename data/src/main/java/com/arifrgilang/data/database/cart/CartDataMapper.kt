package com.arifrgilang.data.database.cart

import com.arifrgilang.domain.model.CartDomainModel


/**
 * Created by arifrgilang on 4/26/2021
 */
interface CartDataMapper {
    fun mapDomainToData(data: CartDomainModel): CartDataModel
    fun mapDataToDomain(data: CartDataModel): CartDomainModel
}

class CartDataMapperImpl: CartDataMapper {
    override fun mapDomainToData(data: CartDomainModel): CartDataModel =
        CartDataModel(
            data.id,
            data.userEmail,
            data.itemId,
            data.itemName,
            data.itemCategory,
            data.itemVariant,
            data.itemBrand,
            data.itemPrice,
            data.currency,
            data.quantity,
            data.itemDesc,
            data.itemPicUrl
        )

    override fun mapDataToDomain(data: CartDataModel): CartDomainModel =
        CartDomainModel(
            data.id,
            data.userEmail,
            data.itemId,
            data.itemName,
            data.itemCategory,
            data.itemVariant,
            data.itemBrand,
            data.itemPrice,
            data.currency,
            data.quantity,
            data.itemDesc,
            data.itemPicUrl
        )
}