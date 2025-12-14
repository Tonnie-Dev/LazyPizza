package com.tonyxlab.lazypizza.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.tonyxlab.lazypizza.data.repository.AuthRepositoryImpl
import com.tonyxlab.lazypizza.data.repository.CartRepositoryImpl
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.screens.cart.CartViewModel
import com.tonyxlab.lazypizza.presentation.screens.details.DetailsViewModel
import com.tonyxlab.lazypizza.presentation.screens.history.HistoryViewModel
import com.tonyxlab.lazypizza.presentation.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::HistoryViewModel)
}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
}
val repositoryModule = module {
    single < CartRepository>{ CartRepositoryImpl() }
    single<AuthRepository>{ AuthRepositoryImpl(get()) }
}







