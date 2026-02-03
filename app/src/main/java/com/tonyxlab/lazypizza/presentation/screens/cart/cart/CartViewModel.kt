package com.tonyxlab.lazypizza.presentation.screens.cart.cart

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.extensions.calculateTotal
import com.tonyxlab.lazypizza.domain.extensions.extractRecommendedAddOnItems
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.toMenuItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.domain.repository.CatalogRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartUiState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias CartBaseViewModel = BaseViewModel<CartUiState, CartUiEvent, CartActionEvent>

class CartViewModel(
    private val cartRepository: CartRepository,
    private val catalogRepository: CatalogRepository
) : CartBaseViewModel() {

    override val initialState: CartUiState
        get() = CartUiState()

    init {
        observeCart()
        observeAddOnItemsCatalog()
    }

    private fun observeCart() {

        cartRepository.menuItems.map { items ->

            val count = items.sumOf { it.counter }

            Triple(items, count, items.calculateTotal())
        }
                .onEach { (items, count, total) ->

                    val suggestedAddOnItems = currentState.suggestedAddOnItems
                    updateState {
                        it.copy(
                                menuItems = items,
                                badgeCount = count,
                                aggregateCartAmount = total,
                                suggestedAddOnItems = items.extractRecommendedAddOnItems(
                                        addOnItems = suggestedAddOnItems
                                )
                        )
                    }
                }
                .launchIn(viewModelScope)
    }

    private fun observeAddOnItemsCatalog() {

        val drinksCatalogFlow = catalogRepository
                .observeAddOnItems(collectionPath = "drinks")

        val iceCreamsCatalogFlow = catalogRepository
                .observeAddOnItems(collectionPath = "ice_creams")

        val saucesCatalogFlow = catalogRepository
                .observeAddOnItems(collectionPath = "sauces")

        combine(drinksCatalogFlow, iceCreamsCatalogFlow, saucesCatalogFlow){
            drinks, creams, sauces ->

            val suggestedItems = (drinks + creams + sauces).shuffled()
            updateState { it.copy(suggestedAddOnItems = suggestedItems) }

        }.launchIn(viewModelScope)

    }

    override fun onEvent(event: CartUiEvent) {
        when (event) {

            is CartUiEvent.IncrementQuantity -> {
                onIncrement(event.item)
            }

            is CartUiEvent.DecrementQuantity -> {
                onDecrement(event.item)
            }

            is CartUiEvent.RemoveItem -> {
                onRemove(event.item)
            }

            CartUiEvent.BackToMenu -> {
                sendActionEvent(CartActionEvent.NavigateBackToMenu)
            }

            is CartUiEvent.SelectAddOn -> selectAddOn(event.addOnItem)

            CartUiEvent.Checkout -> {
                sendActionEvent(CartActionEvent.NavigateToCheckout)
            }
        }
    }

    private fun onIncrement(menuItem: MenuItem) {

        launch {

            cartRepository.updateCount(menuItem = menuItem, newCount = menuItem.counter + 1)
        }
    }

    private fun onDecrement(menuItem: MenuItem) {
        launch {

            cartRepository.updateCount(
                    menuItem = menuItem,
                    newCount = (menuItem.counter - 1).coerceAtLeast(1)
            )

        }
    }

    private fun onRemove(menuItem: MenuItem) {

        launch {

            cartRepository.removeItem(menuItem = menuItem)
        }
        observeAddOnItemsCatalog()
    }

    private fun selectAddOn(addOnItem: AddOnItem) {
        observeAddOnItemsCatalog()
        launch {
            cartRepository.addItem(menuItem = addOnItem.toMenuItem())
        }
    }
}


