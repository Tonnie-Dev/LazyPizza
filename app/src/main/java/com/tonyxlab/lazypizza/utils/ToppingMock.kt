package com.tonyxlab.lazypizza.utils

import com.tonyxlab.lazypizza.domain.model.Topping

/**
 * Mock Topping data for development & preview purposes.
 * Image URLs follow the hosted asset structure:
 * https://pl-coding.com/wp-content/uploads/lazypizza/toppings/{imageUrl}
 */

val mockToppings = listOf(

        Topping(
                id = 1L,
                toppingName = "Bacon",
                toppingPrice = 1.0,
                imageUrl = "bacon.png",
                counter = 0
        ),

        Topping(
                id = 2L,
                toppingName = "Extra Cheese",
                toppingPrice = 1.0,
                imageUrl = "extra_cheese.png",
                counter = 0
        ),

        Topping(
                id = 3L,
                toppingName = "Corn",
                toppingPrice = 0.50,
                imageUrl = "corn.png",
                counter = 0
        ),

        Topping(
                id = 4L,
                toppingName = "Tomato",
                toppingPrice = 0.50,
                imageUrl = "tomato.png",
                counter = 0
        ),

        Topping(
                id = 5L,
                toppingName = "Olives",
                toppingPrice = 0.50,
                imageUrl = "olives.png",
                counter = 0
        ),

        Topping(
                id = 6L,
                toppingName = "Pepperoni",
                toppingPrice = 1.0,
                imageUrl = "pepperoni.png",
                counter = 0
        ),

        Topping(
                id = 7L,
                toppingName = "Mushrooms",
                toppingPrice = 0.50,
                imageUrl = "mushrooms.png",
                counter = 0
        ),

        Topping(
                id = 8L,
                toppingName = "Basil",
                toppingPrice = 0.50,
                imageUrl = "basil.png",
                counter = 0
        ),

        Topping(
                id = 9L,
                toppingName = "Pineapple",
                toppingPrice = 1.0,
                imageUrl = "pineapple.png",
                counter = 0
        ),

        Topping(
                id = 10L,
                toppingName = "Onion",
                toppingPrice = 0.50,
                imageUrl = "onion.png",
                counter = 0
        ),

        Topping(
                id = 11L,
                toppingName = "Chili Peppers",
                toppingPrice = 0.50,
                imageUrl = "chili_peppers.png",
                counter = 0
        ),

        Topping(
                id = 12L,
                toppingName = "Spinach",
                toppingPrice = 0.50,
                imageUrl = "spinach.png",
                counter = 0
        ),
)

/**
 * Helper to build the full hosted URL for each topping image.
 */
fun Topping.fullImageUrl(): String =
    "https://pl-coding.com/wp-content/uploads/lazypizza/toppings/${imageUrl}"
