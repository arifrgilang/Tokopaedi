package com.arifrgilang.presentation.di

import com.arifrgilang.presentation.mapper.*
import com.arifrgilang.presentation.ui.MainViewModel
import com.arifrgilang.presentation.ui.MainViewModelImpl
import com.arifrgilang.presentation.ui.cart.CartRvAdapter
import com.arifrgilang.presentation.ui.cart.CartViewModel
import com.arifrgilang.presentation.ui.cart.CartViewModelImpl
import com.arifrgilang.presentation.ui.checkout.CheckoutRvAdapter
import com.arifrgilang.presentation.ui.checkout.CheckoutViewModel
import com.arifrgilang.presentation.ui.checkout.CheckoutViewModelImpl
import com.arifrgilang.presentation.ui.checkoutdetail.CheckoutDetailRvAdapter
import com.arifrgilang.presentation.ui.checkoutdetail.CheckoutDetailViewModel
import com.arifrgilang.presentation.ui.checkoutdetail.CheckoutDetailViewModelImpl
import com.arifrgilang.presentation.ui.dashboard.DashboardRvAdapter
import com.arifrgilang.presentation.ui.dashboard.DashboardViewModel
import com.arifrgilang.presentation.ui.dashboard.DashboardViewModelImpl
import com.arifrgilang.presentation.ui.history.HistoryRvAdapter
import com.arifrgilang.presentation.ui.history.HistoryViewModel
import com.arifrgilang.presentation.ui.history.HistoryViewModelImpl
import com.arifrgilang.presentation.ui.historydetail.HistoryDetailRvAdapter
import com.arifrgilang.presentation.ui.historydetail.HistoryDetailViewModel
import com.arifrgilang.presentation.ui.historydetail.HistoryDetailViewModelImpl
import com.arifrgilang.presentation.ui.itemdetail.ItemDetailViewModel
import com.arifrgilang.presentation.ui.itemdetail.ItemDetailViewModelImpl
import com.arifrgilang.presentation.ui.promodetail.PromoDetailRvAdapter
import com.arifrgilang.presentation.ui.promodetail.PromoDetailViewModel
import com.arifrgilang.presentation.ui.promodetail.PromoDetailViewModelImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Created by arifrgilang on 4/24/2021
 */
@FlowPreview
@ExperimentalCoroutinesApi
val presentationModule = module {
    // UI Mapper
    single<ItemDomainMapper> {
        ItemDomainMapperImpl()
    }
    single<PromoDomainMapper> {
        PromoDomainMapperImpl(get())
    }
    single<CartDomainMapper> {
        CartDomainMapperImpl()
    }
    single<CheckoutDomainMapper> {
        CheckoutDomainMapperImpl(get())
    }
    single<HistoryDomainMapper> {
        HistoryDomainMapperImpl(get())
    }

    // ViewModel
    viewModel<DashboardViewModel> {
        DashboardViewModelImpl(get(), get(), get(), get(), get(), get(), get(), get())
    }
    viewModel<MainViewModel> {
        MainViewModelImpl(get(), get(), get(), get())
    }
    viewModel<ItemDetailViewModel> {
        ItemDetailViewModelImpl(get(), get(), get())
    }
    viewModel<PromoDetailViewModel> {
        PromoDetailViewModelImpl(get(), get())
    }
    viewModel<CartViewModel> {
        CartViewModelImpl(get(), get(), get(), get(), get())
    }
    viewModel<CheckoutViewModel> {
        CheckoutViewModelImpl(get(), get(), get(), get(), get())
    }
    viewModel<CheckoutDetailViewModel> {
        CheckoutDetailViewModelImpl(get(), get())
    }
    viewModel<HistoryViewModel> {
        HistoryViewModelImpl(get(), get())
    }
    viewModel<HistoryDetailViewModel> {
        HistoryDetailViewModelImpl(get(), get())
    }

    // RecyclerView Adapter
    single {
        DashboardRvAdapter(get())
    }
    single {
        PromoDetailRvAdapter(get())
    }
    single {
        CartRvAdapter(get())
    }
    single {
        CheckoutRvAdapter(get())
    }
    single {
        CheckoutDetailRvAdapter(get())
    }
    single {
        HistoryRvAdapter(get())
    }
    single {
        HistoryDetailRvAdapter(get())
    }
}