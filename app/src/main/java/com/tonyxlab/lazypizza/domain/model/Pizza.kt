package com.tonyxlab.lazypizza.domain.model

import android.app.appsearch.SearchResult

data class Pizza(
    override val id: Long,
    override val name: String,
    val ingredients: List<String>,
    val price: Double,
    val imageUrl: String,
    val description: String? = null,
    override val category: Category
): SearchItem
sealed interface SearchItem{

    val id: Long
    val name: String
    val category: Category
}


data class Topping(
    val id:Long,
    val toppingName: String,
    val toppingPrice: Double,
    val imageUrl: String,
    val counter: Int
)


