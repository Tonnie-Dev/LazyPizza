package com.tonyxlab.lazypizza.data.local.database

import androidx.room.Dao
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
 @Query(
         """
             SELECT * FROM cart_items_table WHERE cart_id = :cartId
         """
 )
    fun getCartItems(cartId: String): Flow<List<CartItemWithTopping>>

    @Upsert
    suspend fun upsertCartItem(cartItemEntity: CartItemEntity): Long

    @Upsert
    suspend fun updateToppings(toppings:List<ToppingEntity>)

    @Query("DELETE FROM cart_items_table WHERE cart_id =:cartId")
    suspend fun clear(cartId: String)
}
