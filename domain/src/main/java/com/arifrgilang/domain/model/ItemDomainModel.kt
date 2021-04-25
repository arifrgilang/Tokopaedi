package com.arifrgilang.domain.model


/**
 * Created by arifrgilang on 4/25/2021
 */
data class ItemDomainModel (
    val id: Int,
    val itemName: String?,
    val itemCategory: String?,
    val itemVariant: String?,
    val itemBrand: String?,
    val itemPrice: Long?,
    val itemDesc: String,
    val itemPicUrl: String
)