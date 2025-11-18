package com.tonyxlab.lazypizza.utils

import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.SideItem

val drinksMock = listOf(
        SideItem(
                id = 1L,
                name = "7-Up",
                price = 1.89,
                imageUrl = "seven.png",
                counter = 0,
                category = Category.DRINKS
        )
)

val iceCreamsMock = listOf(
        SideItem(
                id = 1L,
                name = "Chocolate Ice Cream",
                price = 2.49,
                imageUrl = "chocolate.png",
                counter = 0,
                category = Category.ICE_CREAM
        )
)

val SaucesMock = listOf(
        SideItem(
                id = 1L,
                name = "Garlic Sauce",
                price = 0.59,
                imageUrl = "garlic.png",
                counter = 0,
                category = Category.SAUCE
        )
)



