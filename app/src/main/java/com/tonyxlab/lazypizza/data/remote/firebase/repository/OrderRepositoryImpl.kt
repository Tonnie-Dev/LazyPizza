@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.data.remote.firebase.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction
import com.tonyxlab.lazypizza.data.remote.firebase.dto.OrderDto
import com.tonyxlab.lazypizza.data.remote.firebase.dto.toDomain
import com.tonyxlab.lazypizza.data.remote.firebase.dto.toDto
import com.tonyxlab.lazypizza.domain.model.Order
import com.tonyxlab.lazypizza.domain.repository.OrderRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OrderRepositoryImpl(private val firestore: FirebaseFirestore) : OrderRepository {

    override fun getOrders(userId: String): Flow<List<Order>> = callbackFlow {

        val listener = firestore.collection("orders")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Direction.DESCENDING)
                .addSnapshotListener { snapshot, exception ->

                    if (exception != null) {
                        return@addSnapshotListener
                    }

                    val orders = snapshot
                            ?.documents
                            ?.mapNotNull { document ->
                                document.toObject(OrderDto::class.java)
                                        ?.toDomain()
                            }
                            .orEmpty()

                    trySend(orders)
                }
        awaitClose { listener.remove() }
    }

    override suspend fun saveOrder(order: Order) {

        val documentRef = firestore
                .collection("orders")
                .document()

        documentRef.set(
                order
                        .copy(id = documentRef.id)
                        .toDto()
        )
    }
}