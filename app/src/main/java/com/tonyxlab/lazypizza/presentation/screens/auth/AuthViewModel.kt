package com.tonyxlab.lazypizza.presentation.screens.auth

import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthActionEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUIEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiState

typealias AuthBaseViewModel = BaseViewModel<AuthUiState, AuthUIEvent, AuthActionEvent>
class AuthViewModel: AuthBaseViewModel(){

    override val initialState: AuthUiState
        get() = AuthUiState()

    override fun onEvent(event: AuthUIEvent) {
        TODO("Not yet implemented")
    }
}