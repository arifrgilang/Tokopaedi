package com.arifrgilang.presentation.mapper

import com.arifrgilang.domain.model.CartDomainModel
import com.arifrgilang.presentation.model.CartUiModel


/**
 * Created by arifrgilang on 4/26/2021
 */
interface CartDomainMapper {
    fun mapUiToDomain(data: CartUiModel): CartDomainModel
    fun mapDomainToUi(data: CartDomainModel): CartUiModel
}

class CartDomainMapperImpl: CartDomainMapper {
    override fun mapUiToDomain(data: CartUiModel): CartDomainModel =
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

    override fun mapDomainToUi(data: CartDomainModel): CartUiModel =
        CartUiModel(
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