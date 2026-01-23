package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.MenuItem
import com.tonyxlab.lazypizza.domain.model.ProductType
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body1Medium
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.Body4Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold
import com.tonyxlab.lazypizza.presentation.theme.VerticalRoundedCornerShape12
import com.tonyxlab.lazypizza.utils.menuItemsMocks

@Composable
fun CartItemCard(
    menuItem: MenuItem,
    menuItems: List<MenuItem>,
    cartItemActions: CartItemActions,
    modifier: Modifier = Modifier
) {
    val counter = menuItems.counterFor(menuItem)

    Card(
            modifier = modifier
                    .fillMaxWidth()
                    .border(
                            width = MaterialTheme.spacing.spaceSingleDp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                    ),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(intrinsicSize = IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically
        ) {

            DisplayImage(
                    modifier = Modifier
                            .weight(.3f)
                            .fillMaxHeight(),
                    imageUrl = menuItem.imageUrl,
                    shape = MaterialTheme.shapes.VerticalRoundedCornerShape12,
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    fallbackDrawableRes = R.drawable.drink_seven
            )
            CartItemBody(
                    modifier = Modifier.weight(.7f),
                    menuItem = menuItem,
                    counter = counter,
                    actions = cartItemActions
            )
        }
    }
}

@Composable
private fun CartItemBody(
    menuItem: MenuItem,
    counter: Int,
    actions: CartItemActions,
    modifier: Modifier = Modifier,
) {
    Column(
            modifier = modifier.padding(
                    horizontal = MaterialTheme.spacing.spaceMedium,
                    vertical = MaterialTheme.spacing.spaceTwelve
            ), verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
    ) {
        Column { //Row 1
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                        text = menuItem.name,
                        style = MaterialTheme.typography.Body1Medium.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.W700
                        )
                )

                Box(
                        modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .border(
                                        width = MaterialTheme.spacing.spaceSingleDp,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = MaterialTheme.shapes.small,
                                )
                                .size(MaterialTheme.spacing.spaceDoubleDp * 11)
                                .clickable {
                                    actions.onRemove(menuItem)
                                }, contentAlignment = Alignment.Center
                ) {

                    Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = stringResource(id = R.string.cds_text_delete),
                            tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (menuItem.productType == ProductType.PIZZA && menuItem.toppings.isNotEmpty()) {

                Column {
                    menuItem.toppings.fastForEach { topping ->

                        Text(
                                text = "${topping.counter} x ${topping.toppingName}",
                                style = MaterialTheme.typography.Body3Regular.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        )
                    }
                }
            }
        }

        //Row 2
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {

            CounterItem(
                    modifier = Modifier.weight(1f),
                    onAdd = { actions.onIncrement(menuItem) },
                    onRemove = { actions.onDecrement(menuItem) },
                    counter = counter,
                    maxCount = 5,
                    minCount = 1
            )

            Column(
                    modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End
            ) {

                Text(
                        text = stringResource(
                                id = R.string.dollar_price_tag,
                                menuItem.totalPrice()
                        ),
                        style = MaterialTheme.typography.Title1SemiBold.copy(
                                color = MaterialTheme.colorScheme.onSurface
                        )
                )

                Text(
                        text = stringResource(
                                id = R.string.counter_price_tag, counter, menuItem.unitTotalPrice()
                        ), style = MaterialTheme.typography.Body4Regular.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                )
            }
        }
    }
}

data class CartItemActions(
    val onIncrement: (MenuItem) -> Unit,
    val onDecrement: (MenuItem) -> Unit,
    val onRemove: (MenuItem) -> Unit,
)

val MenuItem.uniqueKey
    get() = listOf(
            id.toString(),
            toppings.sortedBy { it.id }
                    .joinToString(separator = "|") { "${it.id}x${it.counter}" })
            .joinToString("-")

private fun List<MenuItem>.counterFor(menuItem: MenuItem): Int =
    firstOrNull { it.uniqueKey == menuItem.uniqueKey }?.counter ?: 1

private fun MenuItem.unitTotalPrice(): Double {
    val toppingsTotalPrice = toppings.sumOf { it.toppingPrice * it.counter }
    return unitPrice + toppingsTotalPrice
}

private fun MenuItem.totalPrice() = unitTotalPrice() * counter

@PreviewLightDark
@Composable
private fun CartItemCard_Preview() {

    val cartItems = menuItemsMocks
    LazyPizzaTheme {
        Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall)
        ) {
            cartItems.forEach { item ->
                CartItemCard(
                        menuItem = item,
                        menuItems = cartItems,
                        cartItemActions = CartItemActions(
                                onIncrement = {},
                                onDecrement = {},
                                onRemove = {}
                        )
                )
            }
        }
    }
}


