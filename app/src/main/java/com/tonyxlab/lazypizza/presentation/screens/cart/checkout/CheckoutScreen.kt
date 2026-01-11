@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AddOnsSection
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarFour
import com.tonyxlab.lazypizza.presentation.core.components.CartItemActions
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.OrderDetailsSection
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.PickupTimeSection
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.theme.HorizontalRoundedCornerShape24
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.cartItemsMock
import com.tonyxlab.lazypizza.utils.getMockSideItems
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel()
) {

    BaseContentLayout(
            viewModel = viewModel, topBar = {

    }) { }
}

@Composable
fun CheckoutScreenContent(
    uiState: CheckoutUiState,
    onEvent: (CheckoutUiEvent) -> Unit,
    cartItemActions: CartItemActions,
    modifier: Modifier = Modifier
) {

    Column(
            modifier = modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.HorizontalRoundedCornerShape24)
                    .dropShadow(
                            shape = MaterialTheme.shapes.HorizontalRoundedCornerShape24,
                            shadow = Shadow(
                                    radius = 10.dp,
                                    spread = 6.dp,
                                    color = Color(0x40000000),
                                    offset = DpOffset(x = 4.dp, 4.dp)
                            )
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(MaterialTheme.spacing.spaceMedium)
    ) {

        AppTopBarFour(onClick = { onEvent(CheckoutUiEvent.GoBack) })

        PickupTimeSection(
                uiState = uiState,
                onEvent = onEvent
        )

        OrderDetailsSection(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceMedium),
                uiState = uiState,
                onEvent = onEvent,
                cartItemActions = cartItemActions
        )

        AddOnsSection(
                items = getMockSideItems(),
                onAddItem = { onEvent(CheckoutUiEvent.SelectAddOnItem(addOnItem = it)) }
        )
    }
}

@PreviewLightDark
@Composable
private fun CheckoutScreen_Preview() {
    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        //.padding(MaterialTheme.spacing.spaceMedium)
        ) {
            CheckoutScreenContent(
                    modifier = Modifier
                            .fillMaxSize(),
                    uiState = CheckoutUiState(
                            cartItems = cartItemsMock,
                            expanded = false
                    ),
                    onEvent = {},
                    cartItemActions = CartItemActions(
                            onIncrement = {},
                            onDecrement = {},
                            onRemove = {})
            )
        }
    }
}




