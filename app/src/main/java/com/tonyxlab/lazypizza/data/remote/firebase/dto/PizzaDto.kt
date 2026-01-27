package com.tonyxlab.lazypizza.data.remote.firebase.dto

import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.Pizza
import com.tonyxlab.lazypizza.domain.model.fullImageUrl

data class PizzaDto(
    val id: Long = 0L,
    val name: String = "",
    val ingredients: List<String> = emptyList(),
    val price: Double = 0.0,
    val imageUrl: String = "",
    val category: String = ""
)
fun Pizza.toDto(): PizzaDto {
    return PizzaDto(
            id = id,
            name = name,
            ingredients = ingredients,
            price = price,
            imageUrl = fullImageUrl(),
            category = category.name
    )
}

fun PizzaDto.toDomain(): Pizza {
    return Pizza(
            id = id,
            name = name,
            ingredients = ingredients,
            price = price,
            imageUrl = imageUrl,
            category = Category.valueOf(category)
    )
}

