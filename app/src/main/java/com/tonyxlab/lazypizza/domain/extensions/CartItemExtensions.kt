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

fun List<MenuItem>.extractRecommendedAddOnItems(addOnItems: List<AddOnItem>): List<AddOnItem> {
    val selectableItemIds = filterNot { it.productType == ProductType.PIZZA }
            .map { it.id }
            .toSet()

    val unSelectedItems = addOnItems
            .filterNot { it.id in selectableItemIds }
            .shuffled()
    return unSelectedItems
}