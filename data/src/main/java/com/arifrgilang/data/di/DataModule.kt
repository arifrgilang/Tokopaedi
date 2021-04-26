package com.arifrgilang.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.arifrgilang.data.database.OpakuDatabase
import com.arifrgilang.data.database.item.ItemDataMapper
import com.arifrgilang.data.database.item.ItemDataMapperImpl
import com.arifrgilang.data.database.promo.PromoDao
import com.arifrgilang.data.database.promo.PromoDataMapper
import com.arifrgilang.data.database.promo.PromoDataMapperImpl
import com.arifrgilang.data.repository.ItemRepositoryImpl
import com.arifrgilang.data.repository.PromoRepositoryImpl
import com.arifrgilang.domain.repository.ItemRepository
import com.arifrgilang.domain.repository.PromoRepository
import org.koin.dsl.module


/**
 * Created by arifrgilang on 4/25/2021
 */
val dataModule = module {
    single {
        OpakuDatabase.getDatabase(get())
    }

    single {
        get<OpakuDatabase>().itemDao()
    }

    single<ItemDataMapper> {
        ItemDataMapperImpl()
    }

    single<ItemRepository> {
        ItemRepositoryImpl(get(), get())
    }

    single {
        get<OpakuDatabase>().promoDao()
    }

    single<PromoDataMapper> {
        PromoDataMapperImpl(get())
    }

    single<PromoRepository> {
        PromoRepositoryImpl(get(), get())
    }
}