@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AddOnsSection
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarFour
import com.tonyxlab.lazypizza.presentation.core.components.CartItemActions
import com.tonyxlab.lazypizza.presentation.core.components.CartItemCard
import com.tonyxlab.lazypizza.presentation.core.components.uniqueKey
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.CommentBox
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.DatePickerComponent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.OrderButtonSection
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.OrderDetailsHeader
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.PickupTimeSection
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.TimePickerComponent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.theme.HorizontalRoundedCornerShape24
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.cartItemsMock
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
    val isDeviceWide = rememberIsDeviceWide()

    var hasAnimated by remember { mutableStateOf(false) }

    val animatedTotalAmount by animateFloatAsState(
            targetValue = uiState.totalAmount.toFloat(),
            animationSpec = if (hasAnimated) {
                tween()
            } else {
                snap()
            },
            finishedListener = { hasAnimated = true }
    )

    val listState = rememberLazyListState()

    val imeBottom = WindowInsets.ime.getBottom(LocalDensity.current)

    val isKeyboardOpen = imeBottom > 0

    LaunchedEffect(isKeyboardOpen) {
        if (isKeyboardOpen) {

            if (isDeviceWide){

                listState.animateScrollToItem(2)

            }else {

                listState.animateScrollToItem(1)
            }
        }
    }

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

        LazyColumn(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium)
                        .imePadding(),
                state = listState

        ) {
            item {

                AppTopBarFour(
                        onClick = { onEvent(CheckoutUiEvent.GoBack) }
                )
            }

            item {
                PickupTimeSection(
                        uiState = uiState,
                        onEvent = onEvent,
                        isDeviceWide = isDeviceWide
                )
            }

            item {

                OrderDetailsHeader(
                        uiState = uiState,
                        onEvent = onEvent
                )
            }

            if (uiState.expanded) {
                items(
                        items = uiState.cartItems,
                        key = { it.uniqueKey }
                ) { cartItem ->
                    CartItemCard(
                            cartItem = cartItem,
                            cartItems = uiState.cartItems,
                            cartItemActions = cartItemActions
                    )
                }
            }

            item {
                AddOnsSection(
                        items = uiState.suggestedAddOnItems,
                        onAddItem = {
                            onEvent(CheckoutUiEvent.SelectAddOnItem(addOnItem = it))
                        }
                )
            }

            item {
                CommentBox(textFieldState = uiState.textFieldState)
            }

            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceOneTwenty))
            }

        }

        OrderButtonSection(
                modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                        .navigationBarsPadding(),
                totalOrderAmount = animatedTotalAmount.toDouble(),
                onEvent = onEvent,
                isWideDevice = rememberIsDeviceWide()
        )

        DatePickerComponent(
                uiState = uiState,
                onDateSelected = { onEvent(CheckoutUiEvent.SelectDate(date = it)) },
                onDismiss = { onEvent(CheckoutUiEvent.DismissPicker) }
        )


        TimePickerComponent(
                uiState = uiState,
                onTimeSelected = { onEvent(CheckoutUiEvent.SelectTime(time = it)) },
                onEvent = onEvent,
                isDeviceWide = isDeviceWide
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




