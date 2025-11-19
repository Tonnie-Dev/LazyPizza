@file:OptIn(FlowPreview::class)

package com.tonyxlab.lazypizza.presentation.screens.home

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeActionEvent.LaunchDialingPad
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeActionEvent.NavigateToDetailsScreen
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
                sendActionEvent(LaunchDialingPad)
            }

            is HomeUiEvent.ClickOnPizza -> {
                sendActionEvent(NavigateToDetailsScreen(event.id))
            }

            is HomeUiEvent.SelectCategoryTab -> {

                val selectedCategory = event.category
                selectCategory(category = selectedCategory)
                /*
                                updateState {

                                    when (selectedCategory) {

                                        Category.PIZZA -> {

                                            it.copy(
                                                    selectedCategory = event.category,
                                                    filteredPizzaItems = currentState.filteredPizzaItems,
                                                    filteredSideItems = emptyList()

                                            )
                                        }

                                        Category.DRINKS, Category.ICE_CREAM, Category.SAUCE -> {

                                            it.copy(
                                                    selectedCategory = event.category,
                                                    filteredPizzaItems = emptyList(),
                                                    filteredSideItems = it.allSideItems.filter { sideItem ->
                                                        sideItem.category == selectedCategory
                                                    }
                                            )
                                        }
                                    }
                                }
                */
            }

            is HomeUiEvent.ClickOnSideItem -> {}
            HomeUiEvent.AddToCart -> {}
            HomeUiEvent.DecrementQuantity -> {}
            HomeUiEvent.IncrementQuantity -> {}
            HomeUiEvent.ResetOrder -> {}
        }
    }

    private fun observeSearchBarState() {
        val textFlow = snapshotFlow { currentState.textFieldState.text }
        textFlow.debounce(300)
                .distinctUntilChanged()
                .onEach { text ->
                    updateState { it.copy(isTextEmpty = text.isEmpty()) }
                    onSearchQueryChange(text.toString())
                }
                .launchIn(viewModelScope)
    }

    private fun onSearchQueryChange(newQuery: String) {

        val query = newQuery.trim()

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

    private fun selectCategory(category: Category) {

        updateState {
            when (category) {
                Category.PIZZA -> it.copy(
                        selectedCategory = category,
                        filteredPizzaItems = it.allPizzaItems,
                        filteredSideItems = emptyList()
                )

                Category.DRINKS, Category.SAUCE, Category.ICE_CREAM -> it.copy(
                        selectedCategory = category,
                        filteredPizzaItems = emptyList(),
                        filteredSideItems = it.allSideItems.filter { side ->
                            side.category == category
                        }
                )
            }
        }
    }
}