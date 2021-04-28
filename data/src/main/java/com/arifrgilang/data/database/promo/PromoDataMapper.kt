package com.arifrgilang.data.database.promo

import com.arifrgilang.data.database.item.ItemDataMapper
import com.arifrgilang.domain.model.PromoDomainModel


/**
 * Created by arifrgilang on 4/25/2021
 */
interface PromoDataMapper {
    fun mapDomainToData(data: PromoDomainModel): PromoDataModel
    fun mapDataToDomain(data: PromoDataModel): PromoDomainModel
}

class PromoDataMapperImpl(
    private val itemDataMapper: ItemDataMapper
) : PromoDataMapper{
    override fun mapDomainToData(data: PromoDomainModel): PromoDataModel =
        PromoDataModel(
            data.id,
            data.promotionName,
            data.creativeName,
            data.creativeSlot,
            data.locationId,
            data.items.map { itemDataMapper.mapDomainToData(it) }
        )

    override fun mapDataToDomain(data: PromoDataModel): PromoDomainModel =
        PromoDomainModel(
            data.id,
            data.promotionName,
            data.creativeName,
            data.creativeSlot,
            data.locationId,
            data.items.map { itemDataMapper.mapDataToDomain(it) }
        )
}