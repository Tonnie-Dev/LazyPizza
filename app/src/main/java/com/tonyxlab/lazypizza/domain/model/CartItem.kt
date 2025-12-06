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


fun Pizza.toCartItem(): CartItem{

    return CartItem(
            id = id,
            name =name,
            imageUrl = imageUrl,
            unitPrice = price,
            counter = 1,
            toppings = listOf(),
            productType = ProductType.PIZZA
    )
}


fun SideItem.toCartItem(): CartItem{

    return CartItem(
            id = id,
            name = this.name,
            imageUrl = imageUrl,
            unitPrice = price,
            counter = 1,
            toppings = emptyList(),
            productType = ProductType.SIDE_ITEM
    )
}
