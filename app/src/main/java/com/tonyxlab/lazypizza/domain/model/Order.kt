package com.tonyxlab.lazypizza.domain.model

import java.time.LocalDateTime
import kotlin.collections.joinToString

enum class OrderStatus { COMPLETED, IN_PROGRESS, CANCELLED }



data class Order(
    val id: String,
    val userId: String,
    val orderNumber: String,
    val pickupTime: LocalDateTime,
    val items: List<String>,
    val totalAmount: Double,
    val status: OrderStatus,
    val timestamp: LocalDateTime,
)

fun Order.formattedOrderNumber() = "Order #$orderNumber"

fun Order.itemsSummary() = items.joinToString("\n") {


    it
}
