package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.components.CartItemCard
import com.tonyxlab.lazypizza.presentation.core.components.CartItemActions
import com.tonyxlab.lazypizza.presentation.core.components.LazyListComponent
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.cartItemsMock

@Composable
fun OrderDetailsSection(
    uiState: CheckoutUiState,
    onEvent: (CheckoutUiEvent) -> Unit,
    cartItemActions: CartItemActions,
    modifier: Modifier = Modifier
) {

    val (icon, description) = when (uiState.expanded) {

        true -> Icons.Default.KeyboardArrowUp to stringResource(R.string.cds_text_collapse)
        else -> Icons.Default.KeyboardArrowDown to stringResource(R.string.cds_text_collapse)
    }
    Column(modifier = modifier) {

        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.spacing.spaceMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
        ) {
            Text(
                    text = stringResource(id = R.string.header_text_order_details),
                    style = MaterialTheme.typography.Label2SemiBold.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant

                    )
            )

            Box(
                    modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .border(
                                    width = MaterialTheme.spacing.spaceSingleDp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = MaterialTheme.shapes.small
                            )
                            .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                            ) {

                                if (uiState.expanded) onEvent(CheckoutUiEvent.CollapseOrder)
                                else
                                    onEvent(CheckoutUiEvent.ExpandOrder)
                            }
            ) {
                Icon(
                        imageVector = icon,
                        contentDescription = description,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        AnimatedVisibility(visible = uiState.expanded) {
            LazyListComponent(
                    items = uiState.cartItems,
                    key = { it.id },

                    ) { cartItem ->
                CartItemCard(
                        modifier = Modifier,
                        cartItem = cartItem,
                        cartItems = uiState.cartItems,
                        cartItemActions = CartItemActions(
                                onIncrement = {},
                                onDecrement = {},
                                onRemove = {})
                )
            }

            if (!uiState.expanded) {
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
private fun OrderDetailSection_Preview() {
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {
            OrderDetailsSection(
                    uiState = CheckoutUiState(expanded = true, cartItems = cartItemsMock),
                    onEvent = {},
                    cartItemActions = CartItemActions(
                            onIncrement = {},
                            onDecrement = {},
                            onRemove = {})
            )
        }
    }
}
