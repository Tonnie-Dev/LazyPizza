@file:OptIn(FlowPreview::class)

package com.tonyxlab.lazypizza.presentation.screens.home

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.data.repository.CartRepositoryImpl
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.SideItem
import com.tonyxlab.lazypizza.domain.model.toCartItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
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
import org.koin.core.KoinApplication.Companion.init

typealias HomeBaseViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

class HomeViewModel(
    private val repository: CartRepository

) : HomeBaseViewModel() {

    val cartItems = repository.cartItems
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
            }

            is HomeUiEvent.AddSideItemToCart -> {

                addSideItemToCart(sideItem = event.sideItem)

                val cartItem = event.sideItem.toCartItem()
                repository.addItem(cartItem)
            }

            is HomeUiEvent.IncrementQuantity -> {
                incrementCount(sideItem = event.sideItem)

            }

            is HomeUiEvent.DecrementQuantity -> {

                decrementCount(sideItem = event.sideItem)
            }

            is HomeUiEvent.ResetOrderStatus -> { resetOrderStatus(event.sideItem)}
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

        val pizzaSearchResults =
            currentState.allPizzaItems.filter { it.name.contains(query, ignoreCase = true) }

        val sideItemSearchResults = currentState.allSideItems.filter {
            it.name.contains(query, ignoreCase = true)
        }
        updateState { it.copy(searchResults = pizzaSearchResults + sideItemSearchResults) }

    }

    private fun selectCategory(category: Category) {

        updateState {
            when (category) {
                Category.PIZZA -> it.copy(
                        selectedCategory = category,

                        filteredSideItems = emptyList()
                )

                Category.DRINKS, Category.SAUCE, Category.ICE_CREAM -> it.copy(
                        selectedCategory = category,

                        filteredSideItems = it.allSideItems.filter { side ->
                            side.category == category
                        }
                )
            }
        }
    }

    private fun addSideItemToCart(sideItem: SideItem) {
        if (currentState.selectedSideItems.any { it.id == sideItem.id }) return
        adjustPriceForSelectedSideItem(sideItem = sideItem, newCount = 1)
        calculateItemOrderTotal()
    }

    private fun adjustPriceForSelectedSideItem(sideItem: SideItem, newCount: Int) {
        val newSet = currentState.selectedSideItems.toMutableSet()
        newSet.removeIf { it.id == sideItem.id }

        if (newCount > 0) {
            newSet.add(sideItem.copy(counter = newCount))
        }
        updateState { it.copy(selectedSideItems = newSet) }
        calculateItemOrderTotal()
    }

    private fun incrementCount(sideItem: SideItem) {

        val currentCount = currentState.selectedSideItems
                .find { it.id == sideItem.id }?.counter ?: 0

        val newCount = currentCount.plus(1)
                .coerceAtMost(10)
        adjustPriceForSelectedSideItem(sideItem = sideItem, newCount = newCount)
        calculateItemOrderTotal()
    }

    private fun decrementCount(sideItem: SideItem) {
        val currentCount = currentState.selectedSideItems
                .find { it.id == sideItem.id }?.counter ?: 0
        val newCount = currentCount.minus(1)
                .coerceAtLeast(0)
        adjustPriceForSelectedSideItem(sideItem = sideItem, newCount = newCount)
        calculateItemOrderTotal()
    }

    private fun calculateItemOrderTotal() {
        val total = currentState.selectedSideItems.sumOf { it.counter * it.price }
        updateState { it.copy(aggregateItemTotal = total) }
    }

    private fun resetOrderStatus(sideItem: SideItem) {
        val currentSet = currentState.selectedSideItems.toMutableSet()

        currentSet.removeIf { it.id == sideItem.id }

        updateState { it.copy(selectedSideItems = currentSet) }
    }
}