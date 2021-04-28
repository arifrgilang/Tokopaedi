package com.arifrgilang.presentation.mapper

import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.presentation.model.ItemUiModel


/**
 * Created by arifrgilang on 4/25/2021
 */
interface ItemDomainMapper {
    fun mapUiToDomain(data: ItemUiModel): ItemDomainModel
    fun mapDomainToUi(data: ItemDomainModel): ItemUiModel
}

class ItemDomainMapperImpl : ItemDomainMapper {
    override fun mapUiToDomain(data: ItemUiModel): ItemDomainModel =
        ItemDomainModel(
            data.id,
            data.itemName,
            data.itemCategory,
            data.itemVariant,
            data.itemBrand,
            data.itemPrice,
            data.itemDesc,
            data.itemPicUrl
        )

    override fun mapDomainToUi(data: ItemDomainModel): ItemUiModel =
        ItemUiModel(
            data.id,
            data.itemName,
            data.itemCategory,
            data.itemVariant,
            data.itemBrand,
            data.itemPrice,
            data.itemDesc,
            data.itemPicUrl
        )

}