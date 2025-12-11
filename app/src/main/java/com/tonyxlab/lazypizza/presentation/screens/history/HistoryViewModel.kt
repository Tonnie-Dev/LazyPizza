package com.tonyxlab.lazypizza.presentation.screens.history

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryActionEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias HistoryBaseViewModel = BaseViewModel<HistoryUiState, HistoryUiEvent, HistoryActionEvent>

class HistoryViewModel(private val repository: CartRepository) : HistoryBaseViewModel() {

    val cartItems = repository.cartItems

    init {
        observeCount()
    }

    override val initialState: HistoryUiState
        get() = HistoryUiState()

    override fun onEvent(event: HistoryUiEvent) {
        TODO("Not yet implemented")
    }

    private fun observeCount() {

        repository.cartItems.map { items -> items.sumOf { it.counter } }
                .onEach { count ->
                    updateState { it.copy(badgeCount = count) }

                }
                .launchIn(viewModelScope)
    }
}