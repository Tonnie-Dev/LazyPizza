package com.tonyxlab.lazypizza.data.repository

import androidx.room.withTransaction
import com.tonyxlab.lazypizza.data.local.database.CartDao
import com.tonyxlab.lazypizza.data.local.database.LazyPizzaDatabase
import com.tonyxlab.lazypizza.data.local.database.entity.CartEntity
import com.tonyxlab.lazypizza.data.local.database.entity.CartOwnerType
import com.tonyxlab.lazypizza.data.local.database.mappers.toEntity
import com.tonyxlab.lazypizza.data.local.database.mappers.toModel
import com.tonyxlab.lazypizza.data.local.database.mappers.toToppingEntities
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.repository.CartIdProvider
import com.tonyxlab.lazypizza.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class CartRepositoryImpl(
    private val dao: CartDao,
    private val database: LazyPizzaDatabase,
   private val cartIdProvider: CartIdProvider
) : CartRepository {

    // private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())

    override val cartItems: Flow<List<CartItem>> = dao
            .getCartItems(cartIdProvider.currentCartId)
            .map { entities -> entities.map { it.toModel() } }

    override suspend fun addItem(cartItem: CartItem) {
        val cartId = cartIdProvider.currentCartId
        database.withTransaction {


            // ✅ 0. Ensure parent cart exists
            dao.upsertCart(
                    CartEntity(
                            cartId = cartId,
                            ownerType = if (cartId == "GUEST")
                                CartOwnerType.GUEST
                            else
                                CartOwnerType.AUTHENTICATED
                    )
            )
            // 1. Load existing cart items snapshot for comparison
            val existingItems =
                dao.getCartItemsList(cartIdProvider.currentCartId)
            // 2. Compare
            val match = existingItems.firstOrNull {
                it.toModel().isSameAs(cartItem)
            }
            // 3. If item exists, update counter
            if (match != null) {

                dao.upsertCartItem(
                        cartItemEntity = cartItem.copy(
                                counter = match.cartItem.counter + cartItem.counter
                        )
                                .toEntity(cartIdProvider.currentCartId)
                )

            }
            // 4. If item does not exist, insert it as-is
            else {

                val insertedEntityId = dao.upsertCartItem(
                        cartItemEntity = cartItem.toEntity(cartIdProvider.currentCartId)
                )

                dao.upsertToppings(cartItem.toToppingEntities(insertedEntityId))
            }
        }

    }
    /*    override fun addItem(cartItem: CartItem) {
            // convert mutable state flow to mutable list

            val currentCartItemsList = _cartItems.value.toMutableList()
            // check if the item to be added is contained in the existing cart list

            val itemIndex = _cartItems.value.indexOfFirst { it.isSameAs(other = cartItem) }

            if (itemIndex >= 0) {
                //item exists - for updating
                val existingCartItem = currentCartItemsList[itemIndex]
                val updatedItem =
                    existingCartItem.copy(counter = existingCartItem.counter + cartItem.counter)
                currentCartItemsList[itemIndex] = updatedItem
            } else {
                // we add item as a new item
                currentCartItemsList.add(cartItem)
            }
            _cartItems.update { currentCartItemsList }
        }*/

    override suspend fun removeItem(cartItem: CartItem) {

        val cartId = cartIdProvider.currentCartId

        val items = dao.getCartItemsList(cartId)

        val match = items.firstOrNull {
            it.toModel()
                    .isSameAs(cartItem)
        } ?: return

        dao.deleteCartItemById(match.cartItem)

    }

    /*  override fun removeItem(cartItem: CartItem) {

          val updatedList = cartItems.value.filterNot { it.isSameAs(cartItem) }
          _cartItems.update { updatedList }
      }*/
    override suspend fun updateCount(
        cartItem: CartItem,
        newCount: Int
    ) {

        val cartId = cartIdProvider.currentCartId
        database.withTransaction {

            val items = dao.getCartItemsList(cartId)
            val match = items.firstOrNull {
                it.toModel()
                        .isSameAs(cartItem)
            } ?: return@withTransaction

            if (newCount <= 0) {
                dao.deleteCartItemById(match.cartItem)
            } else {

                dao.upsertCartItem(cartItemEntity = match.cartItem.copy(counter = newCount))
            }
        }

    }
    /*
        override fun updateCount(
            cartItem: CartItem,
            newCount: Int
        ) {
            _cartItems.update { list ->
                list.mapNotNull { item ->

                    when {
                        item.isSameAs(cartItem) && newCount <= 0 -> null
                        item.isSameAs(cartItem) -> item.copy(counter = newCount)
                        else -> item
                    }

                }
            }
        }

    */

    override suspend fun clear() {
        val cartId = cartIdProvider.currentCartId
        dao.clear(cartId = cartId)
    }
    /*
        override fun clear() {
            _cartItems.update { emptyList() }
        }
    */
    override suspend fun clearAuthenticatedCart(userId: String) {
        database.withTransaction {
            Timber.tag("CartRepo").i("clear called")
            dao.clear(cartId = userId)
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



