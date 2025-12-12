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

enum class ProductType { PIZZA, SIDE_ITEM }

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

fun SideItem.toCartItem(): CartItem {

    return CartItem(
            id = id,
            name = this.name,
            imageUrl = imageUrl,
            unitPrice = price,
            counter = 1,
            toppings = emptyList(),
            productType = ProductType.SIDE_ITEM,
            category = category
    )
}

fun CartItem.toSideItem(): SideItem {
    return SideItem(
            id = id,
            name = this.name,
            imageUrl = imageUrl,
            price = unitPrice,
            counter = 1,
            category = Category.DRINKS

    )

}
