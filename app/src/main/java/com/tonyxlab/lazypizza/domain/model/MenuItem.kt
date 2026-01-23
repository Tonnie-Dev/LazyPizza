package com.tonyxlab.lazypizza.domain.model

data class MenuItem(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val unitPrice: Double,
    val counter: Int,
    val toppings: List<Topping>,
    val productType: ProductType,
)

enum class ProductType { PIZZA, ADD_ON_ITEM }

fun Pizza.toCartItem(): MenuItem {

    return MenuItem(
            id = id,
            name = name,
            imageUrl = imageUrl,
            unitPrice = price,
            counter = 1,
            toppings = listOf(),
            productType = ProductType.PIZZA,
    )
}

fun AddOnItem.toCartItem(): MenuItem {

    return MenuItem(
            id = id,
            name = this.name,
            imageUrl = imageUrl,
            unitPrice = price,
            counter = 1,
            toppings = emptyList(),
            productType = ProductType.ADD_ON_ITEM,
            )
}


