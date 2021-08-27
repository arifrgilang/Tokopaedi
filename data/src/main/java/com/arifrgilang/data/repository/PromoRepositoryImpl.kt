package com.arifrgilang.data.repository

import com.arifrgilang.data.database.promo.PromoDao
import com.arifrgilang.data.database.promo.PromoDataMapper
import com.arifrgilang.domain.model.PromoDomainModel
import com.arifrgilang.domain.repository.PromoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * Created by arifrgilang on 4/25/2021
 */
class PromoRepositoryImpl(
    private val promoMapper: PromoDataMapper,
    private val promoDao: PromoDao
) : PromoRepository{
    override suspend fun postPromos(promos: List<PromoDomainModel>) {
        promoDao.insertAll(
            promos.map {
                promoMapper.mapDomainToData(it)
            }
        )
    }

    override fun getAllPromo(): Flow<List<PromoDomainModel>> =
        promoDao.getAll().map { items ->
            items.map { promoMapper.mapDataToDomain(it) }
        }

    override fun getPromo(promoId: Int): Flow<PromoDomainModel> =
        promoDao.getPromo(promoId).map {
            promoMapper.mapDataToDomain(it)
        }
}