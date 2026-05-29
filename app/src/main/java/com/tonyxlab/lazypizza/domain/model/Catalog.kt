package com.tonyxlab.lazypizza.domain.model

import android.R.attr.category
import com.tonyxlab.lazypizza.data.remote.firebase.dto.PizzaDto
import com.tonyxlab.lazypizza.utils.Constants.STORAGE_BASE_URL

data class Pizza(
    override val id: Long,
    override val name: String,
    val ingredients: List<String>,
    val price: Double,
    val imageUrl: String,
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
    val counter: Int = 0,
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
    PIZZA(categoryName = "Pizza", folderPath = "pizzas"),
    DRINKS(categoryName = "Drinks", folderPath = "drinks"),
    SAUCE(categoryName = "Sauces", folderPath = "sauces"),
    ICE_CREAM(categoryName = "Ice Cream", folderPath = "ice_creams")
}

fun Pizza.fullImageUrl(): String =
    "$STORAGE_BASE_URL${category.folderPath}%2F$imageUrl?alt=media"

fun AddOnItem.fullImageUrl():String =
    "$STORAGE_BASE_URL${category.folderPath}%2F$imageUrl?alt=media"

fun Topping.fullImageUrl():String =
    "$STORAGE_BASE_URL${"toppings"}%2F$imageUrl?alt=media"





