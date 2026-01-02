package com.tonyxlab.lazypizza.domain.model

data class CartItem(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val unitPrice: Double,
    val counter: Int,
    val toppings: List<Topping>,
    val productType: ProductType,
    val category: Category
)

enum class ProductType { PIZZA, ADD_ON_ITEM }

fun Pizza.toCartItem(): CartItem {

    return CartItem(
            id = id,
            name = name,
            imageUrl = imageUrl,
            unitPrice = price,
            counter = 1,
            toppings = listOf(),
            productType = ProductType.PIZZA,
            category = category
    )
}

fun AddOnItem.toCartItem(): CartItem {

    return CartItem(
            id = id,
            name = this.name,
            imageUrl = imageUrl,
            unitPrice = price,
            counter = 1,
            toppings = emptyList(),
            productType = ProductType.ADD_ON_ITEM,
            category = category
    )
}

fun CartItem.toSideItem(): AddOnItem {
    return AddOnItem(
            id = id,
            name = this.name,
            imageUrl = imageUrl,
            price = unitPrice,
            counter = 1,
            category = Category.DRINKS
    )
}
