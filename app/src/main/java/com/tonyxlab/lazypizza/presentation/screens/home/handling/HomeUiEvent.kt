package com.tonyxlab.lazypizza.presentation.screens.home.handling

import com.tonyxlab.lazypizza.presentation.core.base.handling.UiEvent

sealed interface HomeUiEvent: UiEvent {
    object PlaceCall: HomeUiEvent

}