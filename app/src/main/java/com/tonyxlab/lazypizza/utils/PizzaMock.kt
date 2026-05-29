package com.tonyxlab.lazypizza.utils

import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.Pizza

val pizzasMock = listOf(

        Pizza(
                id = 1L,
                name = "Margherita",
                ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Fresh basil",
                        "Olive oil"
                ),
                price = 8.99,
                imageUrl = "bbq_chicken.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 2L,
                name = "Pepperoni",
                ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Pepperoni"
                ),
                price = 9.99,
                imageUrl ="peperoni.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 3L,
                name = "Hawaiian",
                ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Ham",
                        "Pineapple"
                ),
                price = 10.49,
                imageUrl ="hawaiian.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 4L,
                name = "BBQ Chicken",
                ingredients = listOf(
                        "BBQ sauce",
                        "Mozzarella",
                        "Grilled chicken",
                        "Onion",
                        "Corn"
                ),
                price = 11.49,
                imageUrl ="bbq_chicken.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 5L,
                name = "Four Cheese",
                ingredients = listOf(
                        "Mozzarella",
                        "Gorgonzola",
                        "Parmesan",
                        "Ricotta"
                ),
                price = 11.99,
                imageUrl ="four_cheese.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 6L,
                name = "Veggie Delight",
                ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Mushrooms",
                        "Olives",
                        "Bell pepper",
                        "Onion",
                        "Corn"
                ),
                price = 9.79,
                imageUrl ="veggie_delight.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 7L,
                name = "Meat Lovers",
                ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Pepperoni",
                        "Ham",
                        "Bacon",
                        "Sausage"
                ),
                price = 12.49,
                imageUrl ="meat_lovers.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 8L,
                name = "Spicy Inferno",
                ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Spicy salami",
                        "Jalapeños",
                        "Red chili pepper",
                        "Garlic"
                ),
                price = 11.29,
                imageUrl ="spicy_inferno.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 9L,
                name = "Seafood Special",
                ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Shrimp",
                        "Mussels",
                        "Squid",
                        "Parsley"
                ),
                price = 13.99,
                imageUrl ="seafood_special.png",
                category = Category.PIZZA
        ),

        Pizza(
                id = 10L,
                name = "Truffle Mushroom",
                ingredients = listOf(
                        "Cream sauce",
                        "Mozzarella",
                        "Mushrooms",
                        "Truffle oil",
                        "Parmesan"
                ),
                price = 12.99,
                imageUrl ="truffle_mushroom.png",
                category = Category.PIZZA
        )
)






