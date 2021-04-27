package com.arifrgilang.domain.di

import com.arifrgilang.domain.interactor.cart.*
import com.arifrgilang.domain.interactor.checkout.*
import com.arifrgilang.domain.interactor.history.*
import com.arifrgilang.domain.interactor.item.*
import com.arifrgilang.domain.interactor.promo.*
import org.koin.dsl.module


/**
 * Created by arifrgilang on 4/25/2021
 */
val domainModule = module {
    // Items
    single<GetItemsUseCase> {
        GetItemsUseCaseImpl(get())
    }
    single<GetItemUseCase> {
        GetItemUseCaseImpl(get())
    }
    single<GetItemWithCategoryUseCase> {
        GetItemWithCategoryUseCaseImpl(get())
    }
    single<PostItemsUseCase> {
        PostItemsUseCaseImpl(get())
    }

    // Promo
    single<GetPromosUseCase> {
        GetPromosUseCaseImpl(get())
    }
    single<GetPromoUseCase> {
        GetPromoUseCaseImpl(get())
    }
    single<PostPromoUseCase> {
        PostPromoUseCaseImpl(get())
    }

    // Cart
    single<DelCartWithEmailUseCase> {
        DelCartWithEmailUseCaseImpl(get())
    }
    single<DelCartWithIdUseCase> {
        DelCartWithIdUseCaseImpl(get())
    }
    single<GetCartWithEmail> {
        GetCartWithEmailImpl(get())
    }
    single<PostCartUseCase> {
        PostCartUseCaseImpl(get())
    }

    // Checkout
    single<DelCheckoutWithIdUseCase> {
        DelCheckoutWithIdUseCaseImpl(get())
    }
    single<GetCheckoutWithEmailUseCase> {
        GetCheckoutWithEmailUseCaseImpl(get())
    }
    single<PostCheckoutUseCase> {
        PostCheckoutUseCaseImpl(get())
    }
    single<GetCheckoutWithIdUseCase> {
        GetCheckoutWithIdUseCaseImpl(get())
    }

    // History
    single<GetHistoryWithEmailUseCase> {
        GetHistoryWithEmailUseCaseImpl(get())
    }
    single<PostHistoryUseCase> {
        PostHistoryUseCaseImpl(get())
    }
    single<GetHistoryWithIdUseCase>() {
        GetHistoryWithIdUseCaseImpl(get())
    }
}