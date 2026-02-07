
@file:RequiresApi(Build.VERSION_CODES.O)
package com.tonyxlab.lazypizza.data.remote.firebase.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.tonyxlab.lazypizza.domain.model.Order
import com.tonyxlab.lazypizza.domain.model.OrderStatus
import com.tonyxlab.lazypizza.utils.toLocalDateTime
import com.tonyxlab.lazypizza.utils.toTimestamp

data class OrderDto(
    val id: String = "",
    val userId: String = "",
    val orderNumber: String = "",
    val pickupTime: Timestamp = Timestamp.now(),
    val items: List<String> = emptyList(),
    val totalAmount: Double = 0.0,
    val timestamp: Timestamp = Timestamp.now()
)

fun OrderDto.toDomain() = Order(
        id = id,
        userId = userId,
        orderNumber = orderNumber,
        pickupTime = pickupTime.toLocalDateTime(),
        items =items,
        totalAmount = totalAmount,
        status = OrderStatus.IN_PROGRESS,
        timestamp = timestamp.toLocalDateTime()
)

fun Order.toDto() = OrderDto(
        id =id,
        userId = userId,
        orderNumber = orderNumber,
        pickupTime = pickupTime.toTimestamp(),
        items = items,
        totalAmount = totalAmount,
        timestamp = timestamp.toTimestamp()
)

