package com.tonyxlab.lazypizza.presentation.screens.history

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.firebase.AuthState
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.domain.repository.OrderRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryActionEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias HistoryBaseViewModel = BaseViewModel<HistoryUiState, HistoryUiEvent, HistoryActionEvent>

class HistoryViewModel(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository
) : HistoryBaseViewModel() {

    private var ordersJob: Job? = null

    init {
        observeCount()
        observeAuthState()
    }

    override val initialState: HistoryUiState
        get() = HistoryUiState()

    private fun observeAuthState() {

        authRepository.authState.onEach { state ->

            when (state) {

                AuthState.Authenticated -> {

                    val userId = authRepository.currentUser?.userId ?: return@onEach
                    updateState { it.copy(isSignedIn = true) }
                    startObservingOrders(userId = userId)
                }

                else -> {
                    ordersJob?.cancel()
                    updateState {
                        it.copy(
                                isSignedIn = false,
                                orderItems = emptyList()
                        )
                    }
                }
            }
        }
                .launchIn(viewModelScope)
    }

    private fun startObservingOrders(userId: String) {
        ordersJob?.cancel()

        ordersJob = orderRepository.getOrders(userId = userId)
                .onEach { orders ->
                    updateState { it.copy(orderItems = orders) }
                }
                .launchIn(viewModelScope)
    }

    override fun onEvent(event: HistoryUiEvent) {
        when (event) {
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
}
