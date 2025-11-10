package com.tonyxlab.lazypizza

import android.app.Application
import timber.log.Timber

class LazyPizzaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}