package com.tonyxlab.lazypizza.data.remote.firebase.dto

import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.fullImageUrl

data class AddOnItemDto(
    val id: Long = 0L,
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val category: String = ""
)

fun AddOnItem.toDto() =
    AddOnItemDto(
            id = id,
            name = name,
            price = price,
            imageUrl = fullImageUrl(),
            category = category.name
    )


fun AddOnItemDto.toModel() =
    AddOnItem(
            id = id,
            name = name,
            price = price,
            imageUrl = imageUrl,
            category = Category.valueOf(category)
    )
