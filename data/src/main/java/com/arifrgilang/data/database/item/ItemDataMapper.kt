package com.arifrgilang.data.database.item

import com.arifrgilang.domain.model.ItemDomainModel


/**
 * Created by arifrgilang on 4/25/2021
 */
interface ItemDataMapper {
    fun mapDomainToData(data: ItemDomainModel): ItemDataModel
    fun mapDataToDomain(data: ItemDataModel): ItemDomainModel
}

class ItemDataMapperImpl : ItemDataMapper {
    override fun mapDomainToData(data: ItemDomainModel): ItemDataModel =
        ItemDataModel(
            data.id,
            data.itemName,
            data.itemCategory,
            data.itemVariant,
            data.itemBrand,
            data.itemPrice,
            data.itemDesc,
            data.itemPicUrl
        )

    override fun mapDataToDomain(data: ItemDataModel): ItemDomainModel =
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
}