package com.tonyxlab.lazypizza.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tonyxlab.lazypizza.data.remote.firebase.dto.toDto
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.domain.model.Pizza
import kotlinx.coroutines.tasks.await

/**
 * Mock Pizza data for development & preview purposes.
 * Image URLs follow the hosted asset structure provided by the campus:
 * https://pl-coding.com/wp-content/uploads/lazypizza/{category.folderPath}/{imageUrl}
 */

val mockPizzas = listOf(

        Pizza(
                id = 1L,
                name = "Margherita",
                ingredients = listOf("Tomato sauce", "Mozzarella", "Fresh basil", "Olive oil"),
                price = 8.99,
                imageUrl = "margherita.png",
                description = "A timeless classic with mozzarella, basil and fresh tomato sauce.",
                category = Category.PIZZA
        ),

        Pizza(
                id = 2L,
                name = "Pepperoni",
                ingredients = listOf("Tomato sauce", "Mozzarella", "Pepperoni"),
                price = 9.99,
                imageUrl = "pepperoni.png",
                description = "Spicy and savory slices of pepperoni baked to perfection.",
                category = Category.PIZZA
        ),

        Pizza(
                id = 3L,
                name = "Hawaiian",
                ingredients = listOf("Tomato sauce", "Mozzarella", "Ham", "Pineapple"),
                price = 10.49,
                imageUrl = "hawaiian.png",
                description = "Sweet pineapple meets salty ham in this tropical favorite.",
                category = Category.PIZZA
        ),

        Pizza(
                id = 4L,
                name = "BBQ Chicken",
                ingredients = listOf("BBQ sauce", "Mozzarella", "Chicken", "Red onion"),
                price = 11.49,
                imageUrl = "bbq-chicken.png",
                description = "Tangy BBQ sauce, grilled chicken and onions for smoky perfection.",
                category = Category.PIZZA
        ),

        Pizza(
                id = 5L,
                name = "Veggie Delight",
                ingredients = listOf(
                        "Tomato sauce",
                        "Mozzarella",
                        "Bell peppers",
                        "Olives",
                        "Mushrooms"
                ),
                price = 9.79,
                imageUrl = "veggie.png",
                description = "A colorful veggie-loaded pizza for a lighter bite.",
                category = Category.PIZZA
        ),

        Pizza(
                id = 6L,
                name = "Four Cheese",
                ingredients = listOf("Mozzarella", "Parmesan", "Cheddar", "Gorgonzola"),
                price = 10.99,
                imageUrl = "four-cheese.png",
                description = "A rich blend of four premium cheeses — gooey and golden.",
                category = Category.PIZZA
        ),

        Pizza(
                id = 7L,
                name = "Meat Lovers",
                ingredients = listOf("Tomato sauce", "Mozzarella", "Pepperoni", "Sausage", "Bacon"),
                price = 11.99,
                imageUrl = "meat-lovers.png",
                description = "A hearty pizza stacked with all your favorite meats.",
                category = Category.PIZZA
        ),

        Pizza(
                id = 8L,
                name = "Truffle Mushroom",
                ingredients = listOf("Cream sauce", "Mozzarella", "Mushrooms", "Truffle oil"),
                price = 12.49,
                imageUrl = "truffle-mushroom.png",
                description = "A luxurious blend of earthy mushrooms and aromatic truffle oil.",
                category = Category.PIZZA
        )
)

/**
 * Helper function to generate full hosted image URL for any pizza.
 */
fun Pizza.fullImageUrl(): String =
    "https://pl-coding.com/wp-content/uploads/lazypizza/${category.folderPath}/${imageUrl}"


suspend fun seedPizzas(
    firestore: FirebaseFirestore,
    pizzas: List<Pizza>
) {
    pizzas.forEach { pizza ->
        firestore
                .collection("pizzas")
                .document(pizza.id.toString())
                .set(pizza.toDto())
                .await()
    }
}
