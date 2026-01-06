@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tonyxlab.lazypizza.presentation.screens.cart.cart

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.navigation.AppNavigationRail
import com.tonyxlab.lazypizza.navigation.BottomNavBar
import com.tonyxlab.lazypizza.navigation.MenuScreenDestination
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarThree
import com.tonyxlab.lazypizza.presentation.core.components.EmptyScreenContent
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.components.AddOnItemsSection
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.components.CardItemContent
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.components.uniqueKey
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.cart.handling.CartUiState
import com.tonyxlab.lazypizza.presentation.screens.menu.details.components.StickyAddToCart
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.DeviceType
import com.tonyxlab.lazypizza.utils.SetStatusBarIconsColor
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

    val windowClass = calculateWindowSizeClass(activity)
    val deviceType = DeviceType.fromWindowSizeClass(windowClass)

    val isDeviceWide = deviceType != DeviceType.MOBILE_PORTRAIT

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
                        }
                    },
                    onBackPressed = { activity.finish() },
                    containerColor = MaterialTheme.colorScheme.background
            ) {

                CartScreenContentWide(uiState = uiState, onEvent = viewModel::onEvent)
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
                    }
                },
                onBackPressed = { activity.finish() },
                containerColor = MaterialTheme.colorScheme.background
        ) {
            CartScreenContentNarrow(
                    modifier = Modifier,
                    uiState = uiState,
                    onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
private fun CartScreenContentNarrow(
    uiState: CartUiState,
    onEvent: (CartUiEvent) -> Unit,
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
                CardItemContent(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = MaterialTheme.spacing.spaceSmall),
                        cartItem = item,
                        cartItems = uiState.cartItems,
                        onEvent = onEvent
                )
            }
            item {
                AddOnItemsSection(
                        modifier = Modifier.fillMaxWidth(),
                        items = uiState.suggestedAddOnItems,
                        onEvent = onEvent,
                        isWide = false
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
                CardItemContent(
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = MaterialTheme.spacing.spaceSmall),
                        cartItem = item,
                        cartItems = uiState.cartItems,
                        onEvent = onEvent
                )
            }

        }

        Surface(modifier = Modifier.weight(1f)) {

            Column(modifier = Modifier.padding(all = MaterialTheme.spacing.spaceMedium)) {

                Text(
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceSmall),
                        text = stringResource(R.string.header_text_recommended_options),
                        style = MaterialTheme.typography.Label2SemiBold.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                )

                AddOnItemsSection(
                        modifier = Modifier.fillMaxWidth(),
                        items = uiState.suggestedAddOnItems,
                        onEvent = onEvent
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
                    onEvent = {}
            )
        }
    }
}

