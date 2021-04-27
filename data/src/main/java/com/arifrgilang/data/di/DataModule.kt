package com.arifrgilang.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.arifrgilang.data.database.OpakuDatabase
import com.arifrgilang.data.database.cart.CartDataMapper
import com.arifrgilang.data.database.cart.CartDataMapperImpl
import com.arifrgilang.data.database.checkout.CheckoutDataMapper
import com.arifrgilang.data.database.checkout.CheckoutDataMapperImpl
import com.arifrgilang.data.database.history.HistoryDataMapper
import com.arifrgilang.data.database.history.HistoryDataMapperImpl
import com.arifrgilang.data.database.item.ItemDataMapper
import com.arifrgilang.data.database.item.ItemDataMapperImpl
import com.arifrgilang.data.database.promo.PromoDao
import com.arifrgilang.data.database.promo.PromoDataMapper
import com.arifrgilang.data.database.promo.PromoDataMapperImpl
import com.arifrgilang.data.repository.*
import com.arifrgilang.domain.repository.*
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

    single {
        get<OpakuDatabase>().cartDao()
    }

    single<CartDataMapper> {
        CartDataMapperImpl()
    }

    single<CartRepository> {
        CartRepositoryImpl(get(), get())
    }

    single {
        get<OpakuDatabase>().checkoutDao()
    }

    single<CheckoutDataMapper> {
        CheckoutDataMapperImpl(get())
    }

    single<CheckoutRepository> {
        CheckoutRepositoryImpl(get(), get())
    }

    single {
        get<OpakuDatabase>().historyDao()
    }

    single<HistoryDataMapper> {
        HistoryDataMapperImpl(get())
    }

    single<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }
}