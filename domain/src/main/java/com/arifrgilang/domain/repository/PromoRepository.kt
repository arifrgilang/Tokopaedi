package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.PromoDomainModel
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
interface PromoRepository {
    suspend fun postPromos(promos: List<PromoDomainModel>)
    fun getAllPromo(): Flow<List<PromoDomainModel>>
    fun getPromo(promoId: Int): Flow<PromoDomainModel>
}