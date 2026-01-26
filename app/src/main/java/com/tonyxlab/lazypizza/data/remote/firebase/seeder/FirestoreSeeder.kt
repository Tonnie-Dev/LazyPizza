package com.tonyxlab.lazypizza.data.remote.firebase.seeder

import com.google.firebase.firestore.FirebaseFirestore
import com.tonyxlab.lazypizza.data.remote.firebase.dto.toDto
import com.tonyxlab.lazypizza.domain.model.Pizza
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class FirestoreSeeder(
    private val firestore: FirebaseFirestore
) {

    suspend fun seedPizzas(pizzas: List<Pizza>) {

        Timber.tag("FirestoreSeeder")
                .i("Seeding seedPizzasIfEmpty() called")
        val snapshot = firestore.collection("pizzas")
                .limit(1)
                .get()
                .await()
        if (!snapshot.isEmpty) return // already seeded

        pizzas.forEach { pizza ->

            Timber.tag("FirestoreSeeder")
                    .i("Seeding ${pizza.id} ...")
            firestore
                    .collection("pizzas")
                    .document(pizza.id.toString())
                    .set(pizza.toDto())
                    .await()
        }
    }

    suspend fun clearPizzas() {

        val snapshot = firestore.collection("pizzas")
                .get()
                .await()

        if (snapshot.isEmpty){
            return
        }

        snapshot.documents.forEach { doc -> doc.reference.delete().await()  }
    }
}
