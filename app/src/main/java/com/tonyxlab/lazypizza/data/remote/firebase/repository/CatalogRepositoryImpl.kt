package com.tonyxlab.lazypizza.data.remote.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.tonyxlab.lazypizza.data.remote.firebase.dto.AddOnItemDto
import com.tonyxlab.lazypizza.data.remote.firebase.dto.PizzaDto
import com.tonyxlab.lazypizza.data.remote.firebase.dto.toModel
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.Pizza
import com.tonyxlab.lazypizza.domain.repository.CatalogRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CatalogRepositoryImpl(private val firestore: FirebaseFirestore) : CatalogRepository {
    override fun observePizzas(): Flow<List<Pizza>> = callbackFlow {
        val listenerRegistration = firestore.collection("pizzas")
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val pizzas =
                        snapshot
                                ?.documents
                                ?.mapNotNull { doc -> doc.toObject(PizzaDto::class.java) }
                                ?.map { dto -> dto.toModel() }
                            ?: emptyList()

                    trySend(pizzas)
                }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    override fun observeAddOnItems(collectionPath: String): Flow<List<AddOnItem>> = callbackFlow {

        val listenerRegistration = firestore.collection(collectionPath)
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val addOnItems = snapshot
                            ?.documents
                            ?.mapNotNull { doc ->
                                doc.toObject(AddOnItemDto::class.java)
                            }
                            ?.map { dto -> dto.toModel() }
                        ?: emptyList()

                    trySend(addOnItems)

                }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}