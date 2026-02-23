@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.data.remote.firebase.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Query
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.*
import com.tonyxlab.lazypizza.data.remote.firebase.dto.OrderDto
import com.tonyxlab.lazypizza.data.remote.firebase.dto.toDomain
import com.tonyxlab.lazypizza.data.remote.firebase.dto.toDto
import com.tonyxlab.lazypizza.domain.model.Order
import com.tonyxlab.lazypizza.domain.repository.OrderRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class OrderRepositoryImpl(private val firestore: FirebaseFirestore) : OrderRepository {
    override  fun getOrders(userId: String): Flow<List<Order>> = flow {

    val snapshot = firestore.collection("orders")
            .whereEqualTo("userId",userId)
            .orderBy("timestamp", Direction.DESCENDING)
            .get()
            .await()

        val orders = snapshot.documents.mapNotNull { doc ->

            doc.toObject(OrderDto::class.java)
                    ?.toDomain()
        }


        emit(orders)
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