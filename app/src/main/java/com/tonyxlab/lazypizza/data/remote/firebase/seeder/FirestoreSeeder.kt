package com.tonyxlab.lazypizza.data.remote.firebase.seeder

import com.google.firebase.firestore.FirebaseFirestore
import com.tonyxlab.lazypizza.data.remote.firebase.dto.toDto
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.Pizza
import kotlinx.coroutines.tasks.await

class FirestoreSeeder(private val firestore: FirebaseFirestore) {

    suspend fun seedPizzas(pizzas: List<Pizza>) {
        val snapshot = firestore.collection("pizzas")
                .limit(1)
                .get()
                .await()

        if (!snapshot.isEmpty) return // already seeded

        pizzas.forEach { pizza ->
            firestore
                    .collection("pizzas")
                    .document(pizza.id.toString())
                    .set(pizza.toDto())
                    .await()
        }
    }

    suspend fun seedAddOnItem(items: List<AddOnItem>, collectionPath: String) {

        val snapshot = firestore.collection(collectionPath)
                .limit(1)
                .get()
                .await()

        if (!snapshot.isEmpty) return

        items.forEach { item ->
            firestore.collection(collectionPath)
                    .document("${item.id}")
                    .set(item.toDto())
                    .await()
        }
    }

    suspend fun clearPizzas() {

        val snapshot = firestore.collection("pizzas")
                .get()
                .await()

        if (snapshot.isEmpty) return

        snapshot.documents.forEach { doc ->
            doc.reference.delete()
                    .await()
        }
    }

    suspend fun clearAddOnItem(collectionPath: String) {
        val snapshot = firestore.collection(collectionPath)
                .get()
                .await()

        if (snapshot.isEmpty) return

        snapshot.documents.forEach { doc ->
            doc.reference.delete()
                    .await()
        }
    }
}
