package com.tonyxlab.lazypizza.utils

import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.AddOnItem

// ------------------------ ALL ITEMS ------------------------

fun getMockSideItems(): List<AddOnItem> {
    return drinksMock + saucesMock + iceCreamsMock
}

// ------------------------ ADD-ON ITEMS ------------------------

fun getMockAddOnItems(): List<AddOnItem> {
    return drinksMock + saucesMock
}

// ------------------------ DRINKS ------------------------

val drinksMock = listOf(
        AddOnItem(
                id = 9L,
                name = "Mineral Water",
                price = 1.49,
                imageUrl = "mineral%20water.png",
                category = Category.DRINKS
        ),
        AddOnItem(
                id = 10L,
                name = "7-Up",
                price = 1.89,
                imageUrl = "7-up.png",
                category = Category.DRINKS
        ),
        AddOnItem(
                id = 11L,
                name = "Pepsi",
                price = 1.99,
                imageUrl = "pepsi.png",
                category = Category.DRINKS
        ),
        AddOnItem(
                id = 12L,
                name = "Orange juice",
                price = 2.49,
                imageUrl = "orange%20juice.png",
                category = Category.DRINKS
        ),
        AddOnItem(
                id = 13L,
                name = "Apple Juice",
                price = 2.29,
                imageUrl = "apple juice.png",
                category = Category.DRINKS
        ),
        AddOnItem(
                id = 14L,
                name = "Iced Tea (Lemon)",
                price = 2.19,
                imageUrl = "iced%20tea.png",
                category = Category.DRINKS
        )
)

// ------------------------ SAUCES ------------------------

val saucesMock = listOf(
        AddOnItem(
                id = 15L,
                name = "Garlic Sauce",
                price = 0.59,
                imageUrl = "Garlic%20Sauce.png",
                category = Category.SAUCE
        ),
        AddOnItem(
                id = 16L,
                name = "BBQ Sauce",
                price = 0.59,
                imageUrl = "BBQ%20Sauce.png",
                category = Category.SAUCE
        ),
        AddOnItem(
                id = 17L,
                name = "Cheese Sauce",
                price = 0.89,
                imageUrl = "Cheese%20Sauce.png",
                category = Category.SAUCE
        ),
        AddOnItem(
                id = 18L,
                name = "Spicy Chili Sauce",
                price = 0.59,
                imageUrl = "Spicy%20Chili%20Sauce.png",
                category = Category.SAUCE
        )
)

// ------------------------ ICE CREAMS ------------------------

val iceCreamsMock = listOf(
        AddOnItem(
                id = 19L,
                name = "Vanilla Ice Cream",
                price = 2.49,
                imageUrl = "vanilla.png",
                category = Category.ICE_CREAM
        ),
        AddOnItem(
                id = 20L,
                name = "Chocolate Ice Cream",
                price = 2.49,
                imageUrl = "chocolate.png",
                category = Category.ICE_CREAM
        ),
        AddOnItem(
                id = 21L,
                name = "Strawberry Ice Cream",
                price = 2.49,
                imageUrl = "strawberry.png",
                category = Category.ICE_CREAM
        ),
        AddOnItem(
                id = 22L,
                name = "Cookies Ice Cream",
                price = 2.79,
                imageUrl = "cookies.png",
                category = Category.ICE_CREAM
        ),
        AddOnItem(
                id = 23L,
                name = "Pistachio Ice Cream",
                price = 2.99,
                imageUrl = "pistachio.png",
                category = Category.ICE_CREAM
        ),
        AddOnItem(
                id = 24L,
                name = "Mango Sorbet",
                price = 2.69,
                imageUrl = "mango sorbet.png",
                category = Category.ICE_CREAM
        )
)




