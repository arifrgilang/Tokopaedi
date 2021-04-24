package com.arifrgilang.presentation.di

import com.arifrgilang.presentation.dashboard.DashboardViewModel
import com.arifrgilang.presentation.dashboard.DashboardViewModelImpl
import org.koin.dsl.module


/**
 * Created by arifrgilang on 4/24/2021
 */
val presentationModule = module {
    single<DashboardViewModel> {
        DashboardViewModelImpl()
    }
}