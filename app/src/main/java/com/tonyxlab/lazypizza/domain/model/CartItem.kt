package com.tonyxlab.lazypizza.domain.model

data class CartItem(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val unitPrice: Double,
    val counter: Int,
    val toppings: List<Topping>,
    val productType: ProductType,
)

enum class ProductType { PIZZA, SIDE_ITEM }
