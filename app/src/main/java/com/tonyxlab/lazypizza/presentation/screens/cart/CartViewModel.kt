package com.tonyxlab.lazypizza.presentation.screens.cart

import androidx.lifecycle.viewModelScope
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import com.tonyxlab.lazypizza.presentation.core.base.BaseViewModel
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiState
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

            val countFlow = items.sumOf { it.counter }
            val totalFlow = items.sumOf { it.unitPrice * it.counter }

            Triple(items, countFlow, totalFlow)
        }
                .onEach { (items, count, total) ->

                    updateState {
                        it.copy(
                                cartItems = items,
                                badgeCount = count,
                                aggregateCartAmount = total
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

            CartUiEvent.Checkout -> {
                // Future: trigger checkout flow
            }

            // ❌ This no longer belongs in Cart VM
            is CartUiEvent.PickAddOn -> Unit
        }
    }

    // ✅ Repository delegations
    private fun onIncrement(cartItem: CartItem) {
        repository.updateCount(cartItem = cartItem, newCount = cartItem.counter + 1)
    }

    private fun onDecrement(cartItem: CartItem) {
        repository.updateCount(
                cartItem = cartItem,
                newCount = (cartItem.counter - 1).coerceAtLeast(1)
        )
    }
    /*    private fun onIncrement(item: CartItem) {
            repository.updateCount(
                    itemId = item.id,
                    newCount = item.counter + 1
            )
        }

        private fun onDecrement(item: CartItem) {
            repository.updateCount(
                    itemId = item.id,
                    newCount = (item.counter - 1).coerceAtLeast(1)
            )
        }*/

    private fun onRemove(cartItem: CartItem) {
        repository.removeItem(cartItem = cartItem)
    }
}

/*
class CartViewModel (
    private val cartRepository: CartRepositoryImpl
)

    : CartBaseViewModel(

) {
    override val initialState: CartUiState
        get() = CartUiState()

    override fun onEvent(event: CartUiEvent) {

      */
/*  when (event) {
            is CartUiEvent.IncrementQuantity -> incrementCount(event.item)
            is CartUiEvent.DecrementQuantity -> decrementCount(event.item)
            is CartUiEvent.RemoveItem -> removeFromCart(event.item)
            is CartUiEvent.PickAddOn -> pickAddOn(event.sideItem)
            CartUiEvent.BackToMenu -> navigateToMenu()
            CartUiEvent.Checkout -> {}
        }*//*

    }

    val cartItems = cartRepository.cartItems

    val aggregateAmount = cartItems.map {
        it.sumOf { item -> item.unitPrice * item.counter }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    fun onIncrement(item: CartItem) {
        cartRepository.updateQuantity(item.id, item.counter + 1)
    }

    fun onDecrement(item: CartItem) {
        cartRepository.updateQuantity(item.id, (item.counter - 1).coerceAtLeast(1))
    }

    fun onRemove(item: CartItem) {
        cartRepository.removeItem(item.id)
    }





    */
/*init {

        val initialAggregate = currentState.cartItemsList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(aggregateCartAmount = initialAggregate) }
    }

    private fun incrementCount(cartItem: CartItem) {

        val updatedList = currentState.cartItemsList.map {

            if (it.id == cartItem.id) {
                val newCount = it.counter.plus(1)
                        .coerceAtMost(5)
                it.copy(counter = newCount)
            } else it
        }
        val newAggregate = updatedList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(cartItemsList = updatedList, aggregateCartAmount = newAggregate) }

    }

    private fun decrementCount(cartItem: CartItem) {
        val updatedList = currentState.cartItemsList.map {

            if (it.id == cartItem.id) {
                val newCount = it.counter.minus(1)
                        .coerceAtLeast(1)
                it.copy(counter = newCount)
            } else it
        }

        val newAggregate = updatedList.sumOf { it.unitPrice * it.counter }
        updateState { it.copy(cartItemsList = updatedList, aggregateCartAmount = newAggregate) }

    }

    fun pickAddOn(sideItem: SideItem) {
        val pickedItem = sideItem.toCartItem()

        val updatedPickedAddOnsList =
            currentState.pickedAddOnItemsList
                    .plus(sideItem)
                    .distinctBy { it.id }

        val updatedCartItemsList =
            currentState.cartItemsList
                    .plus(pickedItem)
                    .distinctBy { it.id }

        val updatedAddOnItems =
            currentState.addOnItemsList
                    .filterNot { it.id == sideItem.id }

        val newAggregate = updatedCartItemsList
                .sumOf { it.unitPrice * it.counter }

        updateState {
            it.copy(
                    cartItemsList = updatedCartItemsList,
                    addOnItemsList = updatedAddOnItems,
                    pickedAddOnItemsList = updatedPickedAddOnsList,
                    aggregateCartAmount = newAggregate
            )
        }
    }

    private fun removeFromCart(cartItem: CartItem) {

        val updatedPickedItemsList =
            currentState.pickedAddOnItemsList.filterNot { it.id == cartItem.id }

        val updatedCartList = currentState
                .cartItemsList.filterNot { it.id == cartItem.id }

        val newAggregate = updatedCartList
                .sumOf { it.unitPrice * it.counter }

        val wasPickedAddOn = currentState.pickedAddOnItemsList
                .any { it.id == cartItem.id }

        val updatedAddOnOns = if (wasPickedAddOn)
            currentState.addOnItemsList
                    .plus(cartItem.toSideItem())
                    .shuffled()
        else
            currentState.addOnItemsList

        updateState {
            it.copy(
                    cartItemsList = updatedCartList,
                    addOnItemsList = updatedAddOnOns,
                    pickedAddOnItemsList = updatedPickedItemsList,
                    aggregateCartAmount = newAggregate,
            )
        }
    }

    private fun navigateToMenu() {
        sendActionEvent(actionEvent = CartActionEvent.NavigateBackToMenu)
    }*//*

}
*/
