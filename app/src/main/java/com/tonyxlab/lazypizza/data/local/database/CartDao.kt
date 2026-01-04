package com.tonyxlab.lazypizza.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.tonyxlab.lazypizza.data.local.database.entity.CartItemEntity
import com.tonyxlab.lazypizza.data.local.database.entity.CartItemWithTopping
import com.tonyxlab.lazypizza.data.local.database.entity.ToppingEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Transaction
    @Query("SELECT * FROM cart_items_table ")
    fun getCartItemsFlow(): Flow<List<CartItemWithTopping>>

    @Transaction
    @Query("SELECT * FROM cart_items_table ")
    suspend fun getCartItemsList(): List<CartItemWithTopping>

    @Upsert
    suspend fun upsertCartItem(cartItemEntity: CartItemEntity): Long

    @Upsert
    suspend fun upsertToppings(toppings: List<ToppingEntity>)

    @Delete
    suspend fun deleteCartItemById(cartItemEntity: CartItemEntity)

    @Query("DELETE FROM cart_items_table")
    suspend fun clear()
}
