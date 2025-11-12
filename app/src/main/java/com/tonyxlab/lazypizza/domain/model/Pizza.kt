package com.tonyxlab.lazypizza.domain.model

data class Pizza(
    val id: Long,
    val name: String,
    val toppings: List<String>,
    val price: Double,
    val imageUrl: String,
    val description: String? = null,
    val category: Category
)


