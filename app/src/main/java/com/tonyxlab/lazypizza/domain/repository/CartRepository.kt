package com.tonyxlab.lazypizza.domain.repository

import com.tonyxlab.lazypizza.domain.model.MenuItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    val menuItems: Flow<List<MenuItem>>

    suspend fun addItem(menuItem: MenuItem)
    suspend fun removeItem(menuItem: MenuItem)
    suspend fun updateCount(menuItem: MenuItem, newCount: Int)
    suspend fun clearAuthenticatedCart()

}