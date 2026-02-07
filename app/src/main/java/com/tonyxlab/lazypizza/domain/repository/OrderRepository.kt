package com.tonyxlab.lazypizza.domain.repository

import com.tonyxlab.lazypizza.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getOrders(userId: String): Flow<List<Order>>
    suspend fun saveOrder(order: Order)
}