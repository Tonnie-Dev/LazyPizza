@file:OptIn(FlowPreview::class)

package com.tonyxlab.lazypizza.presentation.screens.menu.menu

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.firebase.AuthState
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.toCartItem
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.menu.menu.handling.MenuActionEvent
import com.tonyxlab.lazypizza.presentation.screens.menu.menu.handling.MenuActionEvent.LaunchDialingPad
import com.tonyxlab.lazypizza.presentation.screens.menu.menu.handling.MenuActionEvent.NavigateToDetailsScreen
import com.tonyxlab.lazypizza.presentation.screens.menu.menu.handling.MenuUiEvent
import com.tonyxlab.lazypizza.presentation.screens.menu.menu.handling.MenuUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias HomeBaseViewModel = BaseViewModel<MenuUiState, MenuUiEvent, MenuActionEvent>

class MenuViewModel(
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : HomeBaseViewModel() {

    init {
        observeSearchBarState()
        observeCart()
        observeAuthState()
    }

    override val initialState: MenuUiState
        get() = MenuUiState()

    private fun observeAuthState() {

        authRepository.authState.onEach { authState ->
            updateState { it.copy(isUserSignedIn = authState is AuthState.Authenticated) }

        }
                .launchIn(viewModelScope)
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

    private fun observeCart() {

        cartRepository.menuItems.map { items ->
            val count = items.sumOf { it.counter }
            items to count
        }
                .onEach { (items, count) ->

                    updateState { it.copy(menuItems = items, badgeCount = count) }
                }
                .launchIn(viewModelScope)
    }

    override fun onEvent(event: MenuUiEvent) {
        when (event) {

            MenuUiEvent.PlaceCall -> {
                sendActionEvent(LaunchDialingPad)
            }

            is MenuUiEvent.ClickOnPizza -> {
                sendActionEvent(NavigateToDetailsScreen(event.id))
            }

            is MenuUiEvent.SelectCategoryTab -> {
                val selectedCategory = event.category
                selectCategory(category = selectedCategory)
            }

            is MenuUiEvent.AddSideItemToCart -> {
                addSideItemToCart(addOnItem = event.addOnItem)
            }

            is MenuUiEvent.IncrementQuantity -> {
                incrementCount(addOnItem = event.addOnItem)
            }

            is MenuUiEvent.DecrementQuantity -> {

                decrementCount(addOnItem = event.addOnItem)
            }

            is MenuUiEvent.ResetOrderStatus -> {
                removeItemFromCart(event.addOnItem)
            }

            MenuUiEvent.SignIn -> {

                sendActionEvent(MenuActionEvent.NavigateToAuthScreen)
            }

            MenuUiEvent.ShowLogoutDialog -> showLogoutDialog()
            MenuUiEvent.DismissLogoutDialog -> dismissLogoutDialog()
            MenuUiEvent.ConfirmLogoutDialog -> confirmLogout()
        }
    }

    private fun onSearchQueryChange(newQuery: String) {

        val query = newQuery.trim()

        val pizzaSearchResults =
            currentState.allPizzaItems.filter { it.name.contains(query, ignoreCase = true) }

        val sideItemSearchResults = currentState.allAddOnItems.filter {
            it.name.contains(query, ignoreCase = true)
        }
        updateState { it.copy(searchResults = pizzaSearchResults + sideItemSearchResults) }

    }

    private fun selectCategory(category: Category) {

        updateState {
            when (category) {
                Category.PIZZA -> it.copy(
                        selectedCategory = category,

                        filteredAddOnItems = emptyList()
                )

                Category.DRINKS, Category.SAUCE, Category.ICE_CREAM -> it.copy(
                        selectedCategory = category,

                        filteredAddOnItems = it.allAddOnItems.filter { side ->
                            side.category == category
                        }
                )
            }
        }
    }

    private fun addSideItemToCart(addOnItem: AddOnItem) {

        val cartItem = addOnItem.toCartItem()

        launch {

            cartRepository.addItem(cartItem)
        }

    }

    private fun incrementCount(addOnItem: AddOnItem) {

        val currentCount = currentState.menuItems.find { it.id == addOnItem.id }?.counter ?: 0

        val newCount = currentCount.plus(1)
                .coerceAtMost(5)
        launch {

            cartRepository.updateCount(menuItem = addOnItem.toCartItem(), newCount = newCount)
        }
    }

    private fun decrementCount(addOnItem: AddOnItem) {

        val currentCount = currentState.menuItems.find { it.id == addOnItem.id }?.counter ?: 0

        val newCount = currentCount.minus(1)
                .coerceAtLeast(0)
        launch {

            cartRepository.updateCount(menuItem = addOnItem.toCartItem(), newCount = newCount)
        }

    }

    private fun removeItemFromCart(addOnItem: AddOnItem) {
       launch {

           cartRepository.removeItem(addOnItem.toCartItem())
       }
    }

    private fun dismissLogoutDialog() {
        updateState { it.copy(showLogoutDialog = false) }
    }

    private fun showLogoutDialog() {
        updateState {
            it.copy(showLogoutDialog = true)
        }
    }

    private fun confirmLogout() {
        authRepository.logout()
        updateState { it.copy(showLogoutDialog = false) }
    }
}