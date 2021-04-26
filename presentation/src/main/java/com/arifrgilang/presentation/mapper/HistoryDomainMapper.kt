package com.arifrgilang.presentation.mapper

import com.arifrgilang.domain.model.HistoryDomainModel
import com.arifrgilang.presentation.model.HistoryUiModel


/**
 * Created by arifrgilang on 4/26/2021
 */
interface HistoryDomainMapper {
    fun mapUiToDomain(data: HistoryUiModel): HistoryDomainModel
    fun mapDomainToUi(data: HistoryDomainModel): HistoryUiModel
}

class HistoryDomainMapperImpl(
    private val cartDomainMapper: CartDomainMapper
): HistoryDomainMapper {
    override fun mapUiToDomain(data: HistoryUiModel): HistoryDomainModel =
        HistoryDomainModel(
            data.id,
            data.userEmail,
            data.items.map { cartDomainMapper.mapUiToDomain(it) }
        )

    override fun mapDomainToUi(data: HistoryDomainModel): HistoryUiModel =
        HistoryUiModel(
            data.id,
            data.userEmail,
            data.items.map { cartDomainMapper.mapDomainToUi(it) }
        )
}