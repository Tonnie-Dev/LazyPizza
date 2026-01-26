package com.tonyxlab.lazypizza.utils

import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.ProductType
import com.tonyxlab.lazypizza.domain.model.Topping

val menuItemsMocks = listOf(

        // -------------------- PIZZAS --------------------

        MenuItem(
                id = 1L,
                name = "Margherita",
                imageUrl = "margherita.png",
                unitPrice = 8.99,
                counter = 2,
                toppings = listOf(
                        Topping(1L, "Extra Cheese", 1.49, "cheese.png", 1),
                        Topping(2L, "Pepperoni", 1.99, "pepperoni.png", 2),
                        Topping(3L, "Olive", 2.99, "pepperoni.png", 3),
                        ),
                productType = ProductType.PIZZA,

                ),

        MenuItem(
                id = 2L,
                name = "Pepperoni",
                imageUrl = "pepperoni.png",
                unitPrice = 9.99,
                counter = 1,
                toppings = listOf(
                        Topping(4L, "Extra Pepperoni", 2.49, "pepperoni.png", 2)
                ),
                productType = ProductType.PIZZA,

                ),

        MenuItem(
                id = 6L,
                name = "Four Cheese",
                imageUrl = "four-cheese.png",
                unitPrice = 10.99,
                counter = 1,
                toppings = emptyList(),
                productType = ProductType.PIZZA,

                ),

        MenuItem(
                id = 8L,
                name = "Truffle Mushroom",
                imageUrl = "truffle-mushroom.png",
                unitPrice = 12.49,
                counter = 1,
                toppings = listOf(
                        Topping(5L, "Extra Mushrooms", 1.79, "mushroom.png", 1)
                ),
                productType = ProductType.PIZZA,

                ),

        // -------------------- DRINKS --------------------

        MenuItem(
                id = 11L,
                name = "Pepsi",
                imageUrl = "pepsi.png",
                unitPrice = 1.99,
                counter = 2,
                toppings = emptyList(),
                productType = ProductType.ADD_ON_ITEM,

                ),

        MenuItem(
                id = 12L,
                name = "Orange Juice",
                imageUrl = "orange_juice.png",
                unitPrice = 2.49,
                counter = 1,
                toppings = emptyList(),
                productType = ProductType.ADD_ON_ITEM,

                ),

        // -------------------- SAUCES --------------------

        MenuItem(
                id = 15L,
                name = "Garlic Sauce",
                imageUrl = "garlic_sauce.png",
                unitPrice = 0.59,
                counter = 3,
                toppings = emptyList(),
                productType = ProductType.ADD_ON_ITEM,

                ),

        MenuItem(
                id = 17L,
                name = "Cheese Sauce",
                imageUrl = "cheese_sauce.png",
                unitPrice = 0.89,
                counter = 1,
                toppings = emptyList(),
                productType = ProductType.ADD_ON_ITEM,

                ),

        // -------------------- ICE CREAM --------------------

        MenuItem(
                id = 22L,
                name = "Cookies Ice Cream",
                imageUrl = "cookies_cream.png",
                unitPrice = 2.79,
                counter = 1,
                toppings = emptyList(),
                productType = ProductType.ADD_ON_ITEM,

                ),

        MenuItem(
                id = 24L,
                name = "Mango Sorbet",
                imageUrl = "mango_sorbet.png",
                unitPrice = 2.69,
                counter = 2,
                toppings = emptyList(),
                productType = ProductType.ADD_ON_ITEM,

                )
)
