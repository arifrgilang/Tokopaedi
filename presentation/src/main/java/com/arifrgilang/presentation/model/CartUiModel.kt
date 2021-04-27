package com.arifrgilang.presentation.model


/**
 * Created by arifrgilang on 4/26/2021
 */
data class CartUiModel(
    val id: Int,
    val userEmail: String?,
    val itemId: Int?,
    val itemName: String?,
    val itemCategory: String?,
    val itemVariant: String?,
    val itemBrand: String?,
    val itemPrice: Long?,
    val currency: String?,
    val quantity: Long?,
    val itemDesc: String,
    val itemPicUrl: String
)