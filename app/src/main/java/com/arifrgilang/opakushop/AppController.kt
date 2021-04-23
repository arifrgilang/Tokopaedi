package com.arifrgilang.opakushop

import android.app.Application
import timber.log.Timber


/**
 * Created by arifrgilang on 4/14/2021
 */
class AppController: Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var instance: AppController
    }
}