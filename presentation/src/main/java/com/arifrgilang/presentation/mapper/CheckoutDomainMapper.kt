package com.arifrgilang.presentation.mapper

import com.arifrgilang.domain.model.CheckoutDomainModel
import com.arifrgilang.presentation.model.CheckoutUiModel


/**
 * Created by arifrgilang on 4/26/2021
 */
interface CheckoutDomainMapper {
    fun mapUiToDomain(data: CheckoutUiModel) : CheckoutDomainModel
    fun mapDomainToUi(data: CheckoutDomainModel): CheckoutUiModel
}

class CheckoutDomainMapperImpl(
    private val cartDomainMapper: CartDomainMapper
) : CheckoutDomainMapper {
    override fun mapUiToDomain(data: CheckoutUiModel): CheckoutDomainModel =
        CheckoutDomainModel(
            data.id,
            data.userEmail,
            data.items.map { cartDomainMapper.mapUiToDomain(it) }
        )

    override fun mapDomainToUi(data: CheckoutDomainModel): CheckoutUiModel =
        CheckoutUiModel(
            data.id,
            data.userEmail,
            data.items.map { cartDomainMapper.mapDomainToUi(it) }
        )
}