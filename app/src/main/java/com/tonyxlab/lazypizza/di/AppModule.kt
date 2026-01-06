package com.tonyxlab.lazypizza.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.tonyxlab.lazypizza.data.local.database.LazyPizzaDatabase
import com.tonyxlab.lazypizza.data.local.datastore.DataStore
import com.tonyxlab.lazypizza.data.repository.AuthRepositoryImpl
import com.tonyxlab.lazypizza.data.repository.CartRepositoryImpl
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.screens.auth.AuthViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.CartViewModel
import com.tonyxlab.lazypizza.presentation.screens.details.DetailsViewModel
import com.tonyxlab.lazypizza.presentation.screens.history.HistoryViewModel
import com.tonyxlab.lazypizza.presentation.screens.home.HomeViewModel
import com.tonyxlab.lazypizza.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::AuthViewModel)
}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
}

val dataStoreModule = module {
    single { DataStore(androidContext()) }
}

val coroutineModule = module {
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}
val repositoryModule = module {
    single<CartRepository> {
        CartRepositoryImpl(
                dao = get(),
                database = get(),
        )
    }
    single<AuthRepository> {
        AuthRepositoryImpl(
                firebaseAuth = get(),
                cartRepository = get(),
                appScope = get()
        )
    }
}

val databaseModule = module {

    single {
        Room.databaseBuilder(
                context = androidContext(),
                klass = LazyPizzaDatabase::class.java,
                name = Constants.DATABASE_NAME
        )
                .fallbackToDestructiveMigration(true)
                .build()
    }

    single { get<LazyPizzaDatabase>().dao }
}







