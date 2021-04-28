package com.arifrgilang.presentation.mapper

import com.arifrgilang.domain.model.PromoDomainModel
import com.arifrgilang.presentation.model.PromoUiModel


/**
 * Created by arifrgilang on 4/25/2021
 */
interface PromoDomainMapper {
    fun mapUiToDomain(data: PromoUiModel): PromoDomainModel
    fun mapDomainToUi(data: PromoDomainModel): PromoUiModel
}

class PromoDomainMapperImpl(
    private val itemDomainMapper: ItemDomainMapper
) : PromoDomainMapper {
    override fun mapUiToDomain(data: PromoUiModel): PromoDomainModel =
        PromoDomainModel(
            data.id,
            data.promotionName,
            data.creativeName,
            data.creativeSlot,
            data.locationId,
            data.items.map { itemDomainMapper.mapUiToDomain(it) }
        )

    override fun mapDomainToUi(data: PromoDomainModel): PromoUiModel =
        PromoUiModel(
            data.id,
            data.promotionName,
            data.creativeName,
            data.creativeSlot,
            data.locationId,
            data.items.map { itemDomainMapper.mapDomainToUi(it) }
        )

}