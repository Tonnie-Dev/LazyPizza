package com.tonyxlab.lazypizza.utils

import com.tonyxlab.lazypizza.domain.model.*
import java.time.LocalDateTime
import kotlin.random.Random

object OrderMock {
    private val itemPool = listOf(
            "Margherita",
            "Pepperoni",
            "BBQ Chicken",
            "Pepsi",
            "Garlic Sauce",
            "Cookies Ice Cream"
    )

    fun generateOrders(
        count: Int = 5
    ): List<Order> {
        return (1..count).map { index ->
            val items = randomOrderItems()
            val total = items.sumOf { it.quantity * randomPriceFor(it.name) }

            Order(
                    id = index.toLong(),
                    orderNumber = generateOrderNumber(index),
                    placedAt = LocalDateTime.now().minusDays(Random.nextLong(0, 10)),
                    orderItems = items,
                    totalAmount = total,
                    orderStatus = OrderStatus.entries.toTypedArray()
                            .random()
            )
        }
    }

    private fun randomOrderItems(): List<OrderItem> {
        return itemPool
                .shuffled()
                .take(Random.nextInt(1, 4))
                .map {
                    OrderItem(
                            name = it,
                            quantity = Random.nextInt(1, 3)
                    )
                }
    }

    private fun randomPriceFor(itemName: String): Double =
        when (itemName) {
            "Margherita" -> 8.99
            "Pepperoni" -> 9.99
            "BBQ Chicken" -> 11.49
            "Pepsi" -> 2.49
            "Garlic Sauce" -> 1.99
            "Cookies Ice Cream" -> 3.49
            else -> 5.0
        }

    private fun generateOrderNumber(index: Int): String =
        "LP-${1000 + index}"
}
