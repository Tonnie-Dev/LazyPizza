package com.tonyxlab.lazypizza.domain.repository

import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.domain.model.Pizza
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    fun observePizzas(): Flow<List<Pizza>>
    fun observeAddOnItems(collectionPath: String) :Flow<List<AddOnItem>>
}