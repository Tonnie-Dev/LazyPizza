@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tonyxlab.lazypizza.presentation.screens.cart.cart

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.navigation.AppNavigationRail
import com.tonyxlab.lazypizza.navigation.BottomNavBar
import com.tonyxlab.lazypizza.navigation.CheckoutScreenDestination
import com.tonyxlab.lazypizza.navigation.MenuScreenDestination
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AddOnsSection
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarThree
import com.tonyxlab.lazypizza.presentation.core.components.CartItemActions
import com.tonyxlab.lazypizza.presentation.core.components.CartItemCard
import com.tonyxlab.lazypizza.presentation.core.components.EmptyScreenContent
import com.tonyxlab.lazypizza.presentation.core.components.uniqueKey
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartUiState
import com.tonyxlab.lazypizza.presentation.screens.menu.details.components.StickyAddToCart
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.SetStatusBarIconsColor
import com.tonyxlab.lazypizza.utils.rememberIsDeviceWide
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel()
) {

    SetStatusBarIconsColor(darkIcons = true)

    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalActivity.current ?: return

    val isDeviceWide = rememberIsDeviceWide()

    if (isDeviceWide) {

        Row(modifier = Modifier.fillMaxSize()) {

            AppNavigationRail(
                    navigator = navigator,
                    itemCount = uiState.badgeCount
            )

            BaseContentLayout(
                    modifier = modifier,
                    viewModel = viewModel,
                    topBar = {
                        AppTopBarThree(
                                modifier = Modifier,
                                titleText = stringResource(id = R.string.topbar_text_cart)
                        )
                    },
                    bottomBar = {},
                    actionEventHandler = { _, action ->
                        when (action) {
                            CartActionEvent.NavigateBackToMenu -> {
                                navigator.navigate(MenuScreenDestination)
                            }

                            CartActionEvent.NavigateToCheckout -> {
                                navigator.navigate(CheckoutScreenDestination)
                            }
                        }
                    },
                    onBackPressed = { activity.finish() },
                    containerColor = MaterialTheme.colorScheme.background
            ) {

                CartScreenContentWide(
                        uiState = uiState,
                        onEvent = viewModel::onEvent,
                        cartItemActions = CartItemActions(
                                onIncrement = {
                                    viewModel.onEvent(
                                            event = CartUiEvent.IncrementQuantity(item = it)
                                    )
                                },
                                onDecrement = {
                                    viewModel.onEvent(
                                            event = CartUiEvent.DecrementQuantity(item = it)
                                    )
                                },
                                onRemove = {
                                    viewModel.onEvent(
                                            event = CartUiEvent.RemoveItem(item = it)
                                    )
                                }
                        )
                )
            }
        }

    } else {

        BaseContentLayout(
                modifier = modifier,
                viewModel = viewModel,
                topBar = {
                    AppTopBarThree(
                            modifier = Modifier,
                            titleText = stringResource(id = R.string.topbar_text_cart)
                    )
                },
                bottomBar = {
                    BottomNavBar(
                            navigator = navigator,
                            itemCount = it.badgeCount
                    )
                },
                actionEventHandler = { _, action ->
                    when (action) {
                        CartActionEvent.NavigateBackToMenu -> {
                            navigator.navigate(MenuScreenDestination)
                        }

                        CartActionEvent.NavigateToCheckout -> {
                            navigator.navigate(CheckoutScreenDestination)
                        }
                    }

                },
                onBackPressed = { activity.finish() },
                containerColor = MaterialTheme.colorScheme.background
        ) {
            CartScreenContentNarrow(
                    modifier = Modifier,
                    uiState = uiState,
                    onEvent = viewModel::onEvent,
                    cartItemActions = CartItemActions(
                            onIncrement = {
                                viewModel.onEvent(
                                        event = CartUiEvent.IncrementQuantity(item = it)
                                )
                            },
                            onDecrement = {
                                viewModel.onEvent(
                                        event = CartUiEvent.DecrementQuantity(item = it)
                                )
                            },
                            onRemove = {
                                viewModel.onEvent(
                                        event = CartUiEvent.RemoveItem(item = it)
                                )
                            }
                    )
            )
        }
    }
}

@Composable
private fun CartScreenContentNarrow(
    uiState: CartUiState,
    onEvent: (CartUiEvent) -> Unit,
    cartItemActions: CartItemActions,
    modifier: Modifier = Modifier,
) {

    var hasAnimated by remember { mutableStateOf(false) }
    val animatedTotal by animateFloatAsState(
            targetValue = uiState.aggregateCartAmount.toFloat(),

            animationSpec = if (hasAnimated) {
                tween(
                        durationMillis = 450,
                        easing = FastOutSlowInEasing
                )
            } else {
                snap()
            },
            label = "CheckoutTotalAnimation",
            finishedListener = {hasAnimated = true}
    )

    if (uiState.cartItems.isEmpty()) {

        EmptyScreenContent(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(top = MaterialTheme.spacing.spaceTwelve * 10),
                title = stringResource(R.string.cap_text_empty_cart),
                subTitle = stringResource(R.string.cap_text_head_back),
                buttonText = stringResource(R.string.btn_text_back_to_menu),
                onEvent = { onEvent(CartUiEvent.BackToMenu) }
        )
        return
    }

    Box(
            modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                    .padding(top = MaterialTheme.spacing.spaceMedium)
    ) {

        LazyColumn(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = MaterialTheme.spacing.spaceOneHundred),

                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(
                    items = uiState.cartItems,
                    key = { it.uniqueKey }
            ) { item ->
                CartItemCard(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = MaterialTheme.spacing.spaceSmall),
                        cartItem = item,
                        cartItems = uiState.cartItems,
                        cartItemActions = cartItemActions
                )
            }
            item {
                AddOnsSection(
                        modifier = Modifier.fillMaxWidth(),
                        items = uiState.suggestedAddOnItems,
                        onAddItem = { onEvent(CartUiEvent.SelectAddOn(addOnItem = it)) },
                )
            }
        }
        StickyAddToCart(
                modifier = Modifier
                        .align(alignment = Alignment.BottomCenter),

                buttonText = stringResource(
                        R.string.btn_text_proceed_to_checkout,
                        animatedTotal
                ),
                onEvent = { onEvent(CartUiEvent.Checkout) }
        )
    }
}

@Composable
private fun CartScreenContentWide(
    uiState: CartUiState,
    onEvent: (CartUiEvent) -> Unit,
    cartItemActions: CartItemActions,
    modifier: Modifier = Modifier,
) {

    val animatedTotal by animateFloatAsState(
            targetValue = uiState.aggregateCartAmount.toFloat(),
            animationSpec = tween(
                    durationMillis = 450,
                    easing = FastOutSlowInEasing
            ),
            label = "CheckoutTotalAnimation"
    )

    if (uiState.cartItems.isEmpty()) {

        EmptyScreenContent(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(top = MaterialTheme.spacing.spaceTwelve * 10),
                title = stringResource(R.string.cap_text_empty_cart),
                subTitle = stringResource(R.string.cap_text_head_back),
                buttonText = stringResource(R.string.btn_text_back_to_menu),
                onEvent = { onEvent(CartUiEvent.BackToMenu) }
        )
        return
    }

    Row(
            modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.spacing.spaceMedium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)

    ) {

        LazyColumn(
                modifier = Modifier
                        .weight(1f)
                        .padding(bottom = MaterialTheme.spacing.spaceOneHundred),

                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(
                    items = uiState.cartItems,
                    key = { it.uniqueKey }
            ) { item ->
                CartItemCard(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = MaterialTheme.spacing.spaceSmall),
                        cartItem = item,
                        cartItems = uiState.cartItems,
                        cartItemActions = cartItemActions
                )
            }

        }

        Surface(modifier = Modifier.weight(1f)) {

            Column(modifier = Modifier.padding(all = MaterialTheme.spacing.spaceMedium)) {

                AddOnsSection(
                        modifier = Modifier.fillMaxWidth(),
                        items = uiState.suggestedAddOnItems,
                        onAddItem = { onEvent(CartUiEvent.SelectAddOn(addOnItem = it)) }
                )

                StickyAddToCart(
                        modifier = Modifier,
                        buttonText = stringResource(
                                R.string.btn_text_proceed_to_checkout,
                                animatedTotal
                        ),
                        onEvent = { onEvent(CartUiEvent.Checkout) }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenContent_Narrow_Preview() {

    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(all = MaterialTheme.spacing.spaceMedium)
                        .fillMaxSize()
        ) {
            CartScreenContentNarrow(
                    uiState = CartUiState(),
                    onEvent = {},
                    cartItemActions = CartItemActions(
                            onIncrement = {},
                            onDecrement = {},
                            onRemove = {})
            )
        }
    }
}

