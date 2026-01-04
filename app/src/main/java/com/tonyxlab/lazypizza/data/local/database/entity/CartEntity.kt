package com.tonyxlab.lazypizza.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

import androidx.room.PrimaryKey
import androidx.room.Relation
import com.tonyxlab.lazypizza.domain.model.ProductType

/*@Entity(tableName = "cart_table")
data class CartEntity(
    @PrimaryKey
    @ColumnInfo(name = "cart_id")
    val cartId: String,
    @ColumnInfo(name = "cart_owner_type")
    val ownerType: CartOwnerType
)

enum class CartOwnerType {
    GUEST, AUTHENTICATED
}*/

@Entity(tableName = "cart_items_table")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_item_id")
    val cartItemId: Long = 0L,
    @ColumnInfo(name = "product_id")
    val productId: Long = 0L,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "unit_price")
    val unitPrice: Double,
    @ColumnInfo(name = "counter")
    val counter: Int,
    @ColumnInfo(name = "product_type")
    val productType: ProductType,
)

@Entity(
        tableName = "toppings_table",
        foreignKeys = [ForeignKey(
                entity = CartItemEntity::class,
                parentColumns = arrayOf("cart_item_id"),
                childColumns = arrayOf("cart_item_id"),
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE
        )]
)

data class ToppingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "topping_id")
    val toppingId: Long= 0L,
    @ColumnInfo(name = "cart_item_id")
    val cartItemId: Long,
    @ColumnInfo(name = "topping_product_id")
    val toppingProductId: Long,
    @ColumnInfo(name = "topping_name")
    val toppingName: String,
    @ColumnInfo(name = "topping_price")
    val toppingPrice: Double,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "counter")
    val counter: Int
)

data class CartItemWithTopping(
    @Embedded
    val cartItem: CartItemEntity,
    @Relation(
            parentColumn = "cart_item_id",
            entityColumn = "cart_item_id"
    )
    val toppings: List<ToppingEntity>
)

