package com.tonyxlab.lazypizza.presentation.screens.cart

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.ProductType
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.toCartItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiState
import com.tonyxlab.lazypizza.utils.getMockSideItems
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

typealias CartBaseViewModel = BaseViewModel<CartUiState, CartUiEvent, CartActionEvent>

class CartViewModel(private val repository: CartRepository) : CartBaseViewModel() {

    override val initialState: CartUiState
        get() = CartUiState()

    init {
        observeCart()
    }

    private fun observeCart() {

        repository.cartItems.map { items ->

            val count = items.sumOf { it.counter }

            val total = items.sumOf { cartItem ->

                val toppingsAmount = cartItem.toppings.sumOf {
                    it.toppingPrice * it.counter
                }

                val unitTotalPrice = cartItem.unitPrice + toppingsAmount
                unitTotalPrice * cartItem.counter
            }

            Triple(items, count, total)
        }
                .onEach { (items, count, total) ->
                    updateState {
                        it.copy(
                                cartItems = items,
                                badgeCount = count,
                                aggregateCartAmount = total,
                                suggestedAddOnItems = deriveSuggestedAddOns(items)
                        )
                    }
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

            CartUiEvent.Checkout -> {
                // Future Milestone
            }
        }
    }

    private fun onIncrement(cartItem: CartItem) {

        launch {

            repository.updateCount(cartItem = cartItem, newCount = cartItem.counter + 1)
        }
    }

    private fun onDecrement(cartItem: CartItem) {
        launch {

            repository.updateCount(
                    cartItem = cartItem,
                    newCount = (cartItem.counter - 1).coerceAtLeast(1)
            )

        }
    }

    private fun onRemove(cartItem: CartItem) {

        launch {

            repository.removeItem(cartItem = cartItem)
        }
    }

    private fun selectAddOn(addOnItem: AddOnItem) {
   launch {

       repository.addItem(cartItem = addOnItem.toCartItem())
   }
    }

    private fun deriveSuggestedAddOns(cartItems: List<CartItem>): List<AddOnItem> {
        val selectedAddOnItemIds = cartItems.filterNot {
            it.productType == ProductType.PIZZA
        }
                .map { it.id }
                .toSet()

        val unSelectedSideItems = getMockSideItems()
                .filterNot { it.id in selectedAddOnItemIds }
                .shuffled()

        return unSelectedSideItems
    }
}


