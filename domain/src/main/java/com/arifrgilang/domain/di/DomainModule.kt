package com.arifrgilang.domain.di

import com.arifrgilang.domain.interactor.item.*
import com.arifrgilang.domain.interactor.promo.*
import org.koin.dsl.module


/**
 * Created by arifrgilang on 4/25/2021
 */
val domainModule = module {
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

    single<GetPromosUseCase> {
        GetPromosUseCaseImpl(get())
    }

    single<GetPromoUseCase> {
        GetPromoUseCaseImpl(get())
    }

    single<PostPromoUseCase> {
        PostPromoUseCaseImpl(get())
    }

}