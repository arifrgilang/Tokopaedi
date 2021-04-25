package com.arifrgilang.domain.model


/**
 * Created by arifrgilang on 4/25/2021
 */
data class PromoDomainModel (
    val id: Int,
    val promotionName: String?,
    val creativeName: String?,
    val creativeSlot: String?,
    val locationId: String?,
    val items: List<ItemDomainModel> = listOf()
)