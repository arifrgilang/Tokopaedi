package com.arifrgilang.presentation.di

import com.arifrgilang.presentation.mapper.ItemDomainMapper
import com.arifrgilang.presentation.mapper.ItemDomainMapperImpl
import com.arifrgilang.presentation.mapper.PromoDomainMapper
import com.arifrgilang.presentation.mapper.PromoDomainMapperImpl
import com.arifrgilang.presentation.ui.MainViewModel
import com.arifrgilang.presentation.ui.MainViewModelImpl
import com.arifrgilang.presentation.ui.dashboard.DashboardRvAdapter
import com.arifrgilang.presentation.ui.dashboard.DashboardViewModel
import com.arifrgilang.presentation.ui.dashboard.DashboardViewModelImpl
import com.arifrgilang.presentation.ui.itemdetail.ItemDetailViewModel
import com.arifrgilang.presentation.ui.itemdetail.ItemDetailViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by arifrgilang on 4/24/2021
 */
val presentationModule = module {
    single<ItemDomainMapper> {
        ItemDomainMapperImpl()
    }

    single<PromoDomainMapper> {
        PromoDomainMapperImpl(get())
    }

    viewModel<DashboardViewModel> {
        DashboardViewModelImpl(get(), get())
    }

    viewModel<MainViewModel> {
        MainViewModelImpl(get(), get(), get(), get())
    }

    viewModel<ItemDetailViewModel> {
        ItemDetailViewModelImpl(get(), get())
    }

    single {
        DashboardRvAdapter(get())
    }
}