package com.arifrgilang.domain.model


/**
 * Created by arifrgilang on 4/26/2021
 */
data class HistoryDomainModel(
    val id: Int,
    val userEmail: String?,
    val items: List<CartDomainModel>
)