package com.tonyxlab.lazypizza.presentation.screens.home

import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiState

typealias HomeBaseViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>
class HomeViewModel: HomeBaseViewModel() {

    override val initialState: HomeUiState
        get() = HomeUiState()

    override fun onEvent(event: HomeUiEvent) {
        when(event){

            HomeUiEvent.PlaceCall -> {
sendActionEvent(HomeActionEvent.NavigateToWhereSunNeverShines)
            }
        }
    }
}