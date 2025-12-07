package com.tonyxlab.lazypizza.presentation.screens.cart.components

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
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.ProductType
import com.tonyxlab.lazypizza.presentation.core.components.CounterItem
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.handling.CartUiState
import com.tonyxlab.lazypizza.presentation.screens.home.components.LazyCategoryList
import com.tonyxlab.lazypizza.presentation.theme.Body1Medium
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.Body4Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold
import com.tonyxlab.lazypizza.presentation.theme.VerticalRoundedCornerShape12
import com.tonyxlab.lazypizza.utils.cartItemsMock

@Composable
fun CartItemList(
    uiState: CartUiState,
    onEvent: (CartUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyCategoryList(
            modifier = modifier,
            items = uiState.cartItemsList,
            key = { it.id }
    ) { item ->

        CardItemContent(
                modifier = Modifier,
                cartItem = item,
                uiState = uiState,
                onEvent = onEvent
        )
    }
}

@Composable
private fun CardItemContent(
    cartItem: CartItem,
    uiState: CartUiState,
    onEvent: (CartUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val counter = uiState.cartItemsList.counterFor(cartItem)

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
                modifier = modifier
                        .fillMaxWidth()
                        .height(intrinsicSize = IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically
        ) {

            DisplayImage(
                    modifier = Modifier
                            .weight(.3f)
                            .fillMaxHeight(),
                    imageUrl = cartItem.imageUrl,
                    shape = MaterialTheme.shapes.VerticalRoundedCornerShape12,
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    fallbackDrawableRes = R.drawable.drink_seven
            )
            CartItemMainContent(
                    modifier = Modifier.weight(.7f),
                    cartItem = cartItem,
                    counter = counter,
                    aggregatePrice = cartItem.unitPrice * counter,
                    onEvent = onEvent
            )
        }
    }
}

@Composable
private fun CartItemMainContent(
    cartItem: CartItem,
    counter: Int,
    aggregatePrice: Double,
    onEvent: (CartUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
            modifier = modifier
                    .padding(
                            horizontal = MaterialTheme.spacing.spaceMedium,
                            vertical = MaterialTheme.spacing.spaceTwelve
                    ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
    ) {
        Column {
            //Row 1
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                        text = cartItem.name,
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
                                    onEvent(CartUiEvent.RemoveItem(item = cartItem))
                                },
                        contentAlignment = Alignment.Center
                ) {

                    Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = stringResource(id = R.string.cds_text_delete),
                            tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (cartItem.productType == ProductType.PIZZA && cartItem.toppings.isNotEmpty()) {

                Column {
                    cartItem.toppings.fastForEach { topping ->

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
                    onAdd = { onEvent(CartUiEvent.IncrementQuantity(item = cartItem)) },
                    onRemove = { onEvent(CartUiEvent.DecrementQuantity(item = cartItem)) },
                    counter = counter,
                    maxCount = 5,
                    minCount = 1
            )

            Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
            ) {

                Text(
                        text = stringResource(
                                id = R.string.dollar_price_tag,
                                aggregatePrice
                        ),
                        style = MaterialTheme.typography.Title1SemiBold.copy(
                                color = MaterialTheme.colorScheme.onSurface
                        )
                )

                Text(
                        text = stringResource(
                                id = R.string.counter_price_tag,
                                counter,
                                cartItem.unitPrice
                        ),
                        style = MaterialTheme.typography.Body4Regular.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                )
            }
        }
    }
}

private fun List<CartItem>.counterFor(cartItem: CartItem): Int =
    firstOrNull { it.id == cartItem.id }?.counter ?: 1

@PreviewLightDark
@Composable
private fun CartItemContent_Preview() {

    val items = cartItemsMock
    LazyPizzaTheme {
        CartItemList(uiState = CartUiState(), onEvent = {})
    }
}


