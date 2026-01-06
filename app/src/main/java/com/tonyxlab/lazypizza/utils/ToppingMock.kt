package com.tonyxlab.lazypizza.utils

import com.tonyxlab.lazypizza.domain.model.Topping

/**
 * Mock Topping data for development & preview purposes.
 * Image URLs follow the hosted asset structure:
 * https://pl-coding.com/wp-content/uploads/lazypizza/toppings/{imageUrl}
 */

val mockToppings = listOf(

        Topping(
                id = 25L,
                toppingName = "Bacon",
                toppingPrice = 1.0,
                imageUrl = "bacon.png",
                counter = 0
        ),

        Topping(
                id =26L,
                toppingName = "Extra Cheese",
                toppingPrice = 1.0,
                imageUrl = "extra_cheese.png",
                counter = 0
        ),

        Topping(
                id = 27L,
                toppingName = "Corn",
                toppingPrice = 0.50,
                imageUrl = "corn.png",
                counter = 0
        ),

        Topping(
                id = 28L,
                toppingName = "Tomato",
                toppingPrice = 0.50,
                imageUrl = "tomato.png",
                counter = 0
        ),

        Topping(
                id = 29L,
                toppingName = "Olives",
                toppingPrice = 0.50,
                imageUrl = "olives.png",
                counter = 0
        ),

        Topping(
                id = 30L,
                toppingName = "Pepperoni",
                toppingPrice = 1.0,
                imageUrl = "pepperoni.png",
                counter = 0
        ),

        Topping(
                id = 31L,
                toppingName = "Mushrooms",
                toppingPrice = 0.50,
                imageUrl = "mushrooms.png",
                counter = 0
        ),

        Topping(
                id = 32L,
                toppingName = "Basil",
                toppingPrice = 0.50,
                imageUrl = "basil.png",
                counter = 0
        ),

        Topping(
                id = 33L,
                toppingName = "Pineapple",
                toppingPrice = 1.0,
                imageUrl = "pineapple.png",
                counter = 0
        ),

        Topping(
                id = 34L,
                toppingName = "Onion",
                toppingPrice = 0.50,
                imageUrl = "onion.png",
                counter = 0
        ),

        Topping(
                id = 35L,
                toppingName = "Chili Peppers",
                toppingPrice = 0.50,
                imageUrl = "chili_peppers.png",
                counter = 0
        ),

        Topping(
                id = 36L,
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
