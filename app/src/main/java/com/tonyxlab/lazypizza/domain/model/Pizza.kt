package com.tonyxlab.lazypizza.domain.model

data class Pizza(
    val id: Long,
    val name: String,
    val ingredients: List<String>,
    val price: Double,
    val imageUrl: String,
    val description: String? = null,
    val category: Category
)

data class Topping(
    val toppingName: String,
    val toppingPrice: Double,
    val imageUrl: String,
    val counter: Int
)


