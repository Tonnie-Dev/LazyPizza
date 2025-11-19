package com.tonyxlab.lazypizza.domain.model

data class SideItem(
    override val id: Long,
    override val name: String,
    val price: Double,
    val imageUrl: String,
    val counter: Int,
    override val category: Category
): SearchItem