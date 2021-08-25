package com.arifrgilang.tokopaedi

import androidx.multidex.MultiDexApplication
import com.arifrgilang.data.di.dataModule
import com.arifrgilang.domain.di.domainModule
import com.arifrgilang.presentation.di.presentationModule
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


/**
 * Created by arifrgilang on 4/14/2021
 */
class AppController: MultiDexApplication(){
    override fun onCreate() {
        super.onCreate()
        instance = this

        Hawk.init(this@AppController).build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@AppController)
            modules(
//                appModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }

    companion object {
        lateinit var instance: AppController
    }
}