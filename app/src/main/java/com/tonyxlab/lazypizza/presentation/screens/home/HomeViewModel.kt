@file:OptIn(FlowPreview::class)

package com.tonyxlab.lazypizza.presentation.screens.home

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toCollection

typealias HomeBaseViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

class HomeViewModel(private val repository: CartRepository) : HomeBaseViewModel() {

    val cartItems = repository.cartItems

    init {
        observeSearchBarState()
        observeBadgeCount()
    }

    override val initialState: HomeUiState
        get() = HomeUiState()

    fun observeBadgeCount() {

        val badgeCountFlow = cartItems.map { items -> items.sumOf { it.counter } }

       badgeCountFlow.onEach { count ->

           updateState { it.copy(badgeCount = count ) }

       }.launchIn(viewModelScope)



    }
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

            is HomeUiEvent.ResetOrderStatus -> {
                removeItemFromCart(event.sideItem)
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
        repository.updateCount(sideItem.toCartItem(), newCount)
    }

    private fun decrementCount(sideItem: SideItem) {
        val currentCount = currentState.selectedSideItems
                .find { it.id == sideItem.id }?.counter ?: 0
        val newCount = currentCount.minus(1)
                .coerceAtLeast(0)
        adjustPriceForSelectedSideItem(sideItem = sideItem, newCount = newCount)
        calculateItemOrderTotal()
        repository.updateCount(sideItem.toCartItem(), newCount)
    }

    private fun calculateItemOrderTotal() {
        val total = currentState.selectedSideItems.sumOf { it.counter * it.price }
        updateState { it.copy(aggregateItemTotal = total) }
    }

    private fun removeItemFromCart(sideItem: SideItem) {

        /**
         *
        val currentSet = currentState.selectedSideItems.toMutableSet()
        currentSet.removeIf { it.id == sideItem.id }
         */
        val currentSet = currentState.selectedSideItems.toMutableSet()
        currentSet.removeIf { it.id == sideItem.id }
        val updatedSet = currentState.selectedSideItems.filterNot {
            it.id == sideItem.id }.toSet()

        updateState { it.copy(selectedSideItems = updatedSet) }
        repository.removeItem(sideItem.id)
    }
}