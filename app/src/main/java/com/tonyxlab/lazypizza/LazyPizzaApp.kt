package com.tonyxlab.lazypizza

import android.app.Application
import com.tonyxlab.lazypizza.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class LazyPizzaApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        startKoin {

            androidContext(androidContext = this@LazyPizzaApp)
            modules(
                    listOf(viewModelModule,)
            )
        }
    }
}