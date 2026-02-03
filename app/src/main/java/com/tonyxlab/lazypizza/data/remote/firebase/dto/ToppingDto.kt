package com.tonyxlab.lazypizza.data.remote.firebase.dto

import com.tonyxlab.lazypizza.domain.model.Topping

data class ToppingDto(
    val id: Long = 0L,
    val toppingName: String = "",
    val toppingPrice: Double = 0.0,
    val imageUrl: String = "",
)

fun ToppingDto.toModel() =
    Topping(
            id = id,
            toppingName = toppingName,
            toppingPrice = toppingPrice,
            imageUrl = imageUrl,
            counter = 0
    )

fun Topping.toDto() = ToppingDto(
        id = id,
        toppingName = toppingName,
        toppingPrice = toppingPrice,
        imageUrl = imageUrl
)
