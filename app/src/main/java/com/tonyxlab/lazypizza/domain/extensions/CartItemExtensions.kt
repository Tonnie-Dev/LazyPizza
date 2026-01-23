package com.tonyxlab.lazypizza.domain.extensions

import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.ProductType
import com.tonyxlab.lazypizza.utils.getMockAddOnItems

fun List<MenuItem>.calculateTotal(): Double {
    val baseAmountPlusToppingsAmount = sumOf { cartItem ->
        val toppingsTotal = cartItem.toppings.sumOf { topping ->
            topping.toppingPrice * topping.counter
        }

        (cartItem.unitPrice + toppingsTotal) * cartItem.counter
    }
    return baseAmountPlusToppingsAmount
}

fun List<MenuItem>.extractRecommendedAddOnItems(): List<AddOnItem> {
    val selectableItemIds = filterNot { it.productType == ProductType.PIZZA }
            .map { it.id }
            .toSet()

    val unSelectedItems =
        getMockAddOnItems()
                .filterNot { it.id in selectableItemIds }
                .shuffled()
    return unSelectedItems
}