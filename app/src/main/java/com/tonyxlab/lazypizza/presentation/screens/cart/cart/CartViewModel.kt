package com.tonyxlab.lazypizza.presentation.screens.cart.cart

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.extensions.calculateTotal
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.toMenuItem
import com.tonyxlab.lazypizza.domain.repository.AuthRepository
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
    private val authRepository: AuthRepository,
    catalogRepository: CatalogRepository
) : CartBaseViewModel() {

    override val initialState: CartUiState
        get() = CartUiState()

    private val cartItemsFlow = cartRepository.menuItems

    private val addOnCatalogFlow =
        combine(
                catalogRepository.observeAddOnItems("drinks"),
                catalogRepository.observeAddOnItems("ice_creams"),
                catalogRepository.observeAddOnItems("sauces")
        ) { drinks, creams, sauces ->
            drinks + creams + sauces
        }

    init {
        observeCart()
        observeSuggestedAddOns()
    }

    private fun observeCart() {

        cartRepository.menuItems.map { items ->

            val count = items.sumOf { it.counter }

            Triple(items, count, items.calculateTotal())
        }
                .onEach { (items, count, total) ->
                    updateState {
                        it.copy(
                                menuItems = items,
                                badgeCount = count,
                                aggregateCartAmount = total

                        )
                    }
                }
                .launchIn(viewModelScope)
    }

    private fun observeSuggestedAddOns() {
        combine(cartItemsFlow, addOnCatalogFlow) {

            cartItems, addOnItems ->

            val cartIds = cartItems.map { it.id }
                    .toSet()

            addOnItems.filterNot { it.id in cartIds }
                    .shuffled()

        }.onEach { suggested ->
            updateState { it.copy(suggestedAddOnItems = suggested) }
        }
                .launchIn(viewModelScope)
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

            CartUiEvent.Checkout -> checkout()
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

    }

    private fun selectAddOn(addOnItem: AddOnItem) {

        launch {
            cartRepository.addItem(menuItem = addOnItem.toMenuItem())
        }
    }

    private fun checkout() {
       if (authRepository.currentUser == null){
           sendActionEvent(CartActionEvent.NavigateToAuth)
       }else {

           sendActionEvent(CartActionEvent.NavigateToCheckout)
       }
    }
}


