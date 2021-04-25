package com.arifrgilang.domain.repository

import com.arifrgilang.domain.model.PromoDomainModel
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
interface PromoRepository {
    suspend fun postPromos(promos: List<PromoDomainModel>)
    suspend fun getAllPromo(): Flow<List<PromoDomainModel>>
    suspend fun getPromo(promoId: Int): Flow<PromoDomainModel>
}