package com.arifrgilang.presentation.model


/**
 * Created by arifrgilang on 4/25/2021
 */
data class PromoUiModel (
    val id: Int,
    val promotionName: String?,
    val creativeName: String?,
    val creativeSlot: String?,
    val locationId: String?,
    val items: List<ItemUiModel> = listOf()
)