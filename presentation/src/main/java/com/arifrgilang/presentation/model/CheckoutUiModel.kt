package com.arifrgilang.presentation.model

import com.arifrgilang.domain.model.CartDomainModel


/**
 * Created by arifrgilang on 4/26/2021
 */
data class CheckoutUiModel(
    val id: Int,
    val userEmail: String?,
    val items: List<CartUiModel>
)