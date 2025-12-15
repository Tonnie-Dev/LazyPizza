package com.tonyxlab.lazypizza.presentation.screens.auth

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthActionEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiState
import com.tonyxlab.lazypizza.utils.isValidInternationalPhone
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias AuthBaseViewModel = BaseViewModel<AuthUiState, AuthUiEvent, AuthActionEvent>

class AuthViewModel : AuthBaseViewModel() {

    override val initialState: AuthUiState
        get() = AuthUiState()

    init {
        observeTextField()
    }

    override fun onEvent(event: AuthUiEvent) {
        TODO("Not yet implemented")
    }

    @OptIn(FlowPreview::class)
    private fun observeTextField() {

        val textFlow = snapshotFlow { currentState.textFieldState.text.toString() }

        textFlow
                .map { it.trim() }
                .debounce(300)
                .distinctUntilChanged()
                .onEach { input ->
                    val isValidNumber = input.isValidInternationalPhone()
                    updateState { it.copy(continueToLogin = isValidNumber) }
                }
                .launchIn(viewModelScope)

    }
}