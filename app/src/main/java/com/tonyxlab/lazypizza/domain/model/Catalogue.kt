package com.tonyxlab.lazypizza.domain.model

data class Pizza(
    override val id: Long,
    override val name: String,
    val ingredients: List<String>,
    val price: Double,
    val imageUrl: String,
    val description: String? = null,
    override val category: Category
) : SearchItem

data class Topping(
    val id: Long,
    val toppingName: String,
    val toppingPrice: Double,
    val imageUrl: String,
    val counter: Int
)

data class AddOnItem(
    override val id: Long,
    override val name: String,
    val price: Double,
    val imageUrl: String,
    val counter: Int,
    override val category: Category
) : SearchItem

sealed interface SearchItem {
    val id: Long
    val name: String
    val category: Category
}

enum class Category(
    val categoryName: String,
    val folderPath: String
) {
    PIZZA(categoryName = "Pizza", folderPath = "pizza"),
    DRINKS(categoryName = "Drinks", folderPath = "drinks"),
    SAUCE(categoryName = "Sauces", folderPath = "sauce"),
    ICE_CREAM(categoryName = "Ice Cream", folderPath = "icecream")
}


