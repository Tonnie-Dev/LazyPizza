package com.tonyxlab.lazypizza.domain.model

import java.time.LocalDateTime

enum class OrderStatus { COMPLETED, IN_PROGRESS, CANCELLED }

data class OrderItem(val name: String, val quantity: Int)

data class Order(
    val id: Long,
    val orderNumber: String,
    val placedAt: LocalDateTime,
    val orderItems: List<OrderItem>,
    val totalAmount: Double,
    val orderStatus: OrderStatus
)

fun Order.formattedOrderNumber() = "Order #$orderNumber"

fun Order.itemsSummary() = orderItems.joinToString("\n") {
    "${it.quantity} x ${it.name}"
}