package com.tonyxlab.lazypizza.domain.model

data class SideItem(
    val id: Long,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val counter: Int,
    val category: Category
)