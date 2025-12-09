package com.tonyxlab.lazypizza.di

import com.tonyxlab.lazypizza.data.repository.CartRepositoryImpl
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


val repositoryModule = module {
    single < CartRepository>{ CartRepositoryImpl() }
}







