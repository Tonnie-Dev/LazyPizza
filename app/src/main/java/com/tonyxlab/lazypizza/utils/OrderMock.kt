@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.Order

import com.tonyxlab.lazypizza.domain.model.OrderStatus
import com.tonyxlab.lazypizza.domain.model.toMenuItem
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
        return buildList {

            repeat(count) { index ->
                val items = randomOrderItems()
                val total = items.sumOf { it.counter * randomPriceFor(it.name) }

                add(
                Order(
                        id ="${index.toLong()}" ,
                        userId = "Tonnie Xiii",
                        orderNumber = generateOrderNumber(index),
                        timestamp = LocalDateTime.now(),
                        items = listOf(),
                        totalAmount = total,
                        status = OrderStatus.entries.toTypedArray()
                                .random(),
                        pickupTime = LocalDateTime.now().plusDays(Random.nextLong(0,10))
                )
                )
            }
        }
    }

    private fun randomOrderItems(): List<MenuItem> {
        return addOnItemsMock().map { it.toMenuItem() }
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
        "Order #${1000 + index}"
}
