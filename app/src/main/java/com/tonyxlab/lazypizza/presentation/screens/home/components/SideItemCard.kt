package com.tonyxlab.lazypizza.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.CartItem
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.presentation.core.components.AppButton
import com.tonyxlab.lazypizza.presentation.core.components.CounterItem
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.lazypizza.presentation.theme.Body1Medium
import com.tonyxlab.lazypizza.presentation.theme.Body4Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold
import com.tonyxlab.lazypizza.presentation.theme.VerticalRoundedCornerShape12
import com.tonyxlab.lazypizza.utils.drinksMock

@Composable
fun SideItemCard(
    addOnItem: AddOnItem,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val counter = uiState.cartItems.counterFor(addOnItem)
    val selected = uiState.cartItems.isSelected(addOnItem)

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
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
        ) {
            DisplayImage(
                    imageUrl = addOnItem.imageUrl,
                    containerSize = MaterialTheme.spacing.spaceTwelve * 10,
                    shape = MaterialTheme.shapes.VerticalRoundedCornerShape12,
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    fallbackDrawableRes = R.drawable.drink_seven
            )
            SideItemCardContent(
                    addOnItem = addOnItem,
                    counter = counter,
                    selected = selected,
                    aggregatePrice = addOnItem.price * counter,
                    onEvent = onEvent
            )
        }
    }
}

@Composable
private fun SideItemCardContent(
    addOnItem: AddOnItem,
    counter: Int,
    selected: Boolean,
    aggregatePrice: Double,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier
                    .padding(
                            horizontal = MaterialTheme.spacing.spaceTwelve,
                            vertical = MaterialTheme.spacing.spaceTwelve
                    ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceTen * 3)
    ) {

        //Row 1
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                    text = addOnItem.name,
                    style = MaterialTheme.typography.Body1Medium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.W700
                    )
            )

            if (selected) {
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
                                    onEvent(HomeUiEvent.ResetOrderStatus(addOnItem = addOnItem))
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
        }

        // Row 2
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {

            if (selected) {

                CounterItem(
                        modifier = Modifier.weight(1f),
                        onAdd = { onEvent(HomeUiEvent.IncrementQuantity(addOnItem)) },
                        onRemove = { onEvent(HomeUiEvent.DecrementQuantity(addOnItem)) },
                        counter = counter,
                        maxCount = 5
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
                                    addOnItem.price
                            ),
                            style = MaterialTheme.typography.Body4Regular.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    )
                }
            } else {

                Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(
                                id = R.string.dollar_price_tag,
                                addOnItem.price
                        ),
                        style = MaterialTheme.typography.Title1SemiBold.copy(
                                color = MaterialTheme.colorScheme.onSurface
                        )
                )

                AppButton(
                        modifier = Modifier.wrapContentWidth(),
                        onClick = { onEvent(HomeUiEvent.AddSideItemToCart(addOnItem = addOnItem)) },
                        buttonText = stringResource(id = R.string.btn_text_add_to_cart),
                        buttonHeight = MaterialTheme.spacing.spaceTen * 4,
                        isOutlineButton = true,
                        contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SideItemCard_Preview() {

    val sideItems = drinksMock

    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {

            LazyCategoryList(
                    header = "Drinks", items = sideItems,
                    key = { it.id },
                    isDeviceWide = false
            ) { item ->

                SideItemCard(
                        modifier = Modifier,
                        addOnItem = item,
                        uiState = HomeUiState(),
                        onEvent = {},
                )
            }
        }
    }
}

private fun List<CartItem>.counterFor(addOnItem: AddOnItem) =
    find { it.id == addOnItem.id }?.counter ?: 0

private fun List<CartItem>.isSelected(addOnItem: AddOnItem): Boolean =
    any { it.id == addOnItem.id }



