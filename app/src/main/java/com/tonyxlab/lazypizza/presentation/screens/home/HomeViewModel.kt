@file:OptIn(FlowPreview::class)

package com.tonyxlab.lazypizza.presentation.screens.home

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

typealias HomeBaseViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

class HomeViewModel : HomeBaseViewModel() {

    init {
        observeSearchBarState()
    }

    override val initialState: HomeUiState
        get() = HomeUiState()

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.PlaceCall -> {
                sendActionEvent(HomeActionEvent.LaunchDialingPad)
            }

            is HomeUiEvent.ClickPizza -> {
                sendActionEvent(HomeActionEvent.NavigateToDetailsScreen(event.id))
            }

            is HomeUiEvent.SelectCategoryTab -> {
                updateState { it.copy(selectedCategory = event.category) }
            }
        }
    }

    private fun observeSearchBarState() {
        val textFlow = snapshotFlow { currentState.textFieldState.text }
        textFlow.debounce(300)
                .distinctUntilChanged()
                .onEach { text ->
                    updateState { it.copy(isTextEmpty = text.isEmpty()) }
                    onSearchQueryChange(text.toString())
                    Timber.tag("HomeVW")
                            .i("text is: $text")
                }
                .launchIn(viewModelScope)
    }

    private fun onSearchQueryChange(newQuery: String) {

        updateState {
            val filteredPizzaItems = if (newQuery.isBlank()) {
                currentState.allPizzaItems
            } else {
                currentState.allPizzaItems.filter { item ->
                    item.name.contains(other = newQuery, ignoreCase = true)
                }
            }
            it.copy(filteredPizzaItems = filteredPizzaItems)
        }
    }
}