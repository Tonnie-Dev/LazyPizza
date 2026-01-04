package com.tonyxlab.lazypizza.data.local.database.mappers

import com.tonyxlab.lazypizza.data.local.database.entity.CartItemEntity
import com.tonyxlab.lazypizza.data.local.database.entity.CartItemWithTopping
import com.tonyxlab.lazypizza.data.local.database.entity.ToppingEntity
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.Topping

fun CartItem.toEntity(cartId: String): CartItemEntity =
    CartItemEntity(
            cartId = cartId,
            productType = productType,
            productId = id,
            name = name,
            imageUrl = imageUrl,
            unitPrice = unitPrice,
            counter = counter,
    )

fun CartItem.toToppingEntities(cartItemId: Long): List<ToppingEntity> =
    toppings.map { topping ->

        with(topping) {
            ToppingEntity(
                    cartItemId = cartItemId,
                    toppingProductId = id,
                    toppingName = toppingName,
                    toppingPrice = toppingPrice,
                    imageUrl = imageUrl,
                    counter = counter
            )
        }
    }

fun ToppingEntity.toModel(): Topping =
    Topping(
            id = toppingProductId,
            toppingName = toppingName,
            toppingPrice = toppingPrice,
            imageUrl = imageUrl,
            counter = counter
    )

fun CartItemWithTopping.toModel(): CartItem =
    CartItem(
            id = cartItem.productId,
            name =cartItem.name,
            imageUrl = cartItem.imageUrl,
            unitPrice = cartItem.unitPrice,
            counter = cartItem.counter,
            productType = cartItem.productType,
            toppings = toppings.map { it.toModel() }
    )