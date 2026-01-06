package com.tonyxlab.lazypizza.data.repository

import androidx.room.withTransaction
import com.tonyxlab.lazypizza.data.local.database.CartDao
import com.tonyxlab.lazypizza.data.local.database.LazyPizzaDatabase
import com.tonyxlab.lazypizza.data.local.database.mappers.toEntity
import com.tonyxlab.lazypizza.data.local.database.mappers.toModel
import com.tonyxlab.lazypizza.data.local.database.mappers.toToppingEntities
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(
    private val dao: CartDao,
    private val database: LazyPizzaDatabase,
) : CartRepository {

    override val cartItems: Flow<List<CartItem>> = dao
            .getCartItemsFlow()
            .map { entities -> entities.map { it.toModel() } }

    override suspend fun addItem(cartItem: CartItem) {

        database.withTransaction {

            // 1. Load existing cart items snapshot for comparison
            val existingItems = dao.getCartItemsList()

            // 2. Compare
            val match = existingItems.firstOrNull { cartItemWithTopping ->
                cartItemWithTopping
                        .toModel()
                        .isSameAs(cartItem)
            }

            // 3. If item exists, update counter
            if (match != null) {

                val safeCount = (match.cartItem.counter + cartItem.counter)
                        .coerceAtMost(5)

                dao.upsertCartItem(
                        cartItemEntity = match.cartItem.copy(
                                counter = safeCount
                        )
                )
            }
            // 4. If item does not exist, insert it as-is
            else {

                // 5. First insert the Cart Items
                val insertedEntityId = dao.upsertCartItem(
                        cartItemEntity = cartItem.toEntity()
                )

                // 6. Insert the Toppings
                cartItem.toppings.ifEmpty { return@withTransaction }
                dao.upsertToppings(cartItem.toToppingEntities(insertedEntityId))
            }
        }
    }

    override suspend fun updateCount(cartItem: CartItem, newCount: Int) {

        val safeCount = newCount.coerceIn(1, 5)
        database.withTransaction {

            val items = dao.getCartItemsList()
            val match = items.firstOrNull {
                it.toModel()
                        .isSameAs(cartItem)
            } ?: return@withTransaction

            if (newCount <= 0) {
                dao.deleteCartItemById(match.cartItem)
            } else {
                dao.upsertCartItem(cartItemEntity = match.cartItem.copy(counter = safeCount))
            }
        }
    }

    override suspend fun removeItem(cartItem: CartItem) {

        val items = dao.getCartItemsList()

        val match = items.firstOrNull {
            it.toModel()
                    .isSameAs(cartItem)
        } ?: return

        dao.deleteCartItemById(match.cartItem)
    }

    override suspend fun clearAuthenticatedCart() {
        database.withTransaction {
            dao.clear()
        }
    }

    private fun CartItem.isSameAs(other: CartItem): Boolean {
        if (this.id != other.id) return false

        val thisToppings =
            this.toppings
                    .filter { it.counter > 0 }
                    .sortedBy { it.id }
                    .map { it.id to it.counter }

        val otherToppings =
            other.toppings
                    .filter { it.counter > 0 }
                    .sortedBy { it.id }
                    .map { it.id to it.counter }

        return thisToppings == otherToppings
    }
}



