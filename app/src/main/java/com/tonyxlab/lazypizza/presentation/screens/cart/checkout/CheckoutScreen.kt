@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.CommentBox
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.OrderButtonSection
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.OrderDetailsSection
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.PickupTimeSection
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.theme.HorizontalRoundedCornerShape24
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.cartItemsMock
import com.tonyxlab.lazypizza.utils.getMockSideItems
import com.tonyxlab.lazypizza.utils.rememberIsDeviceWide
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel()
) {
    BaseContentLayout(
            modifier = modifier,
            viewModel = viewModel,
            actionEventHandler = { _, action ->
                when (action) {
                    CheckoutActionEvent.NavigateBack -> navigator.goBack()

                }

            },
            topBar = {},
            containerColor = MaterialTheme.colorScheme.background
    ) { uiState ->

        CheckoutScreenContent(
             modifier = modifier,
                uiState = uiState,
                onEvent = viewModel::onEvent,
                cartItemActions = CartItemActions(
                        onIncrement = {
                            viewModel.onEvent(event = CheckoutUiEvent.IncrementQuantity(it))
                        },
                        onDecrement = {
                            viewModel.onEvent(event = CheckoutUiEvent.DecrementQuantity(it))
                        },
                        onRemove = {
                            viewModel.onEvent(event = CheckoutUiEvent.RemoveItem(it))
                        }
                )
        )
    }
}

@Composable
fun CheckoutScreenContent(
    uiState: CheckoutUiState,
    onEvent: (CheckoutUiEvent) -> Unit,
    cartItemActions: CartItemActions,
    modifier: Modifier = Modifier
) {

    Box(
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

    ) {

        Column(
                modifier = Modifier
                        .padding(all = MaterialTheme.spacing.spaceMedium)
        ) {
            AppTopBarFour(
                    onClick = { onEvent(CheckoutUiEvent.GoBack) }
            )
            PickupTimeSection(
                    uiState = uiState,
                    onEvent = onEvent,
                    isDeviceWide = rememberIsDeviceWide()
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

            CommentBox(textFieldState = uiState.textFieldState)

        }

        OrderButtonSection(
                modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                        .navigationBarsPadding(),
                totalOrderAmount = 85.10, // TODO: Hook the Total to UI State
                onEvent = onEvent,
                isWideDevice = rememberIsDeviceWide()
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
        ) {
            CheckoutScreenContent(
                    modifier = Modifier
                            .fillMaxSize(),
                    uiState = CheckoutUiState(
                            cartItems = cartItemsMock,
                            expanded = true
                    ),
                    onEvent = {},
                    cartItemActions = CartItemActions(
                            onIncrement = {},
                            onDecrement = {},
                            onRemove = {}
                    )
            )
        }
    }
}




