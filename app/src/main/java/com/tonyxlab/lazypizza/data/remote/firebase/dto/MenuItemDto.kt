package com.tonyxlab.lazypizza.data.remote.firebase.dto

import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.ProductType

data class MenuItemDto(
    val id: Long = 0L,
    val name: String = "",
    val imageUrl: String = "",
    val unitPrice: Double = 0.0,
    val productType: String = ""
)

fun MenuItemDto.toMenuItem()= MenuItem (
        id = id,
        name = name,
        imageUrl = imageUrl,
        unitPrice = unitPrice,
        counter = 1,
        toppings = emptyList(),
        productType = ProductType.valueOf(productType)
)

fun MenuItem.toDto(): MenuItemDto{


    return MenuItemDto(
            id = id,
            name = name,
            imageUrl = imageUrl,
            unitPrice = unitPrice,
            productType= productType.toString()
    )
}


