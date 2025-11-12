package com.tonyxlab.lazypizza.domain.model

enum class Category(
    val categoryName: String,
    val folderPath: String
) {
    PIZZA(categoryName = "Pizza", folderPath = "pizza"),
    DRINKS(categoryName = "Drinks", folderPath = "drinks"),
    SAUCE(categoryName = "Sauces", folderPath = "sauce"),
    ICE_CREAM(categoryName = "Ice Cream", folderPath = "icecream")
}
