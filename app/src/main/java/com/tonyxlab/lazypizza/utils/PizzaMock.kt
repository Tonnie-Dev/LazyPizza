package com.tonyxlab.lazypizza.utils

import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.Pizza

/**
 * Mock Pizza data for development & preview purposes.
 * Image URLs follow the hosted asset structure provided by the campus:
 * https://pl-coding.com/wp-content/uploads/lazypizza/{category.folderPath}/{imageUrl}
 */

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
                imageUrl = "Margherita.png",
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
                imageUrl = "Pepperoni.png",
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
                imageUrl = "Hawaiian.png",
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
                imageUrl = "BBQ%20Chicken.png",
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
                imageUrl = "Four%20Cheese.png",
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
                imageUrl = "Veggie%20Delight.png",
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
                imageUrl = "Meat%20Lovers.png",
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
                imageUrl = "Spicy%20Inferno.png",
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
                imageUrl = "Seafood%20Special.png",
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
                imageUrl = "Truffle%20Mushroom.png",
                category = Category.PIZZA
        )
)






