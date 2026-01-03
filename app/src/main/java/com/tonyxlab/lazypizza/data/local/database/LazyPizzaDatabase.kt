package com.tonyxlab.lazypizza.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tonyxlab.lazypizza.data.local.database.entity.CartEntity
import com.tonyxlab.lazypizza.data.local.database.entity.CartItemEntity
import com.tonyxlab.lazypizza.data.local.database.entity.ToppingEntity

@Database(
        entities = [CartEntity::class, CartItemEntity::class, ToppingEntity::class],
        version = 1,
        exportSchema = false
)
abstract class LazyPizzaDatabase: RoomDatabase(){

    abstract val dao: CartDao
}