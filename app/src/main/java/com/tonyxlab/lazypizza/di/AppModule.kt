package com.tonyxlab.lazypizza.di

import com.tonyxlab.lazypizza.presentation.screens.details.DetailsViewModel
import com.tonyxlab.lazypizza.presentation.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
}
