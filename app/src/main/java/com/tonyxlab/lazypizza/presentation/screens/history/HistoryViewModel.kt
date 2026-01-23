package com.tonyxlab.lazypizza.presentation.screens.history

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.firebase.AuthState
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryActionEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias HistoryBaseViewModel = BaseViewModel<HistoryUiState, HistoryUiEvent, HistoryActionEvent>

class HistoryViewModel(
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : HistoryBaseViewModel() {

    val cartItems = cartRepository.menuItems

    init {
        observeCount()
        observerAuthState()
    }

    override val initialState: HistoryUiState
        get() = HistoryUiState()

    override fun onEvent(event: HistoryUiEvent) {
      when(event){
          HistoryUiEvent.SignIn -> {
              sendActionEvent(actionEvent = HistoryActionEvent.NavigateToAuth)
          }

          HistoryUiEvent.GoToMenu -> {
              sendActionEvent(actionEvent = HistoryActionEvent.NavigateToMenu)
          }
      }
    }

    private fun observeCount() {

        cartRepository.menuItems.map { items -> items.sumOf { it.counter } }
                .onEach { count ->
                    updateState { it.copy(badgeCount = count) }
                }
                .launchIn(viewModelScope)
    }
    private fun observerAuthState() {
        authRepository.authState
                .onEach { authState ->

                  updateState { it.copy(isSignedIn = authState is AuthState.Authenticated) }

        }.launchIn(viewModelScope)
    }

}
