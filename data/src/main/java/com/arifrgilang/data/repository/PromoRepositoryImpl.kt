package com.arifrgilang.data.repository

import com.arifrgilang.data.database.promo.PromoDao
import com.arifrgilang.data.database.promo.PromoDataMapper
import com.arifrgilang.domain.model.PromoDomainModel
import com.arifrgilang.domain.repository.PromoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform


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

    override suspend fun getAllPromo(): List<PromoDomainModel> =
        promoDao.getAll().map { promoMapper.mapDataToDomain(it) }

    override suspend fun getPromo(promoId: Int): PromoDomainModel =
        promoMapper.mapDataToDomain(promoDao.getPromo(promoId))
}