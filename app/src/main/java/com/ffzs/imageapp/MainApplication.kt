package com.ffzs.imageapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午2:15
 */
@HiltAndroidApp
class MainApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}