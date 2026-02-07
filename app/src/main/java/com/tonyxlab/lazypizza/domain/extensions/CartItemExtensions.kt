package com.tonyxlab.lazypizza.domain.extensions

import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.ProductType

fun List<MenuItem>.calculateTotal(): Double {
    val baseAmountPlusToppingsAmount = sumOf { cartItem ->
        val toppingsTotal = cartItem.toppings.sumOf { topping ->
            topping.toppingPrice * topping.counter
        }

        (cartItem.unitPrice + toppingsTotal) * cartItem.counter
    }
    return baseAmountPlusToppingsAmount
}

