package com.tonyxlab.lazypizza

import android.app.Application
import com.tonyxlab.lazypizza.di.coroutineModule

import com.tonyxlab.lazypizza.di.databaseModule
import com.tonyxlab.lazypizza.di.firebaseModule
import com.tonyxlab.lazypizza.di.repositoryModule
import com.tonyxlab.lazypizza.di.dataStoreModule
import com.tonyxlab.lazypizza.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class LazyPizzaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(androidContext = this@LazyPizzaApp)
            modules(
                    listOf(
                            viewModelModule,
                            repositoryModule,
                            firebaseModule,
                            databaseModule,
                            dataStoreModule,
                            coroutineModule
                    )
            )
        }
    }
}