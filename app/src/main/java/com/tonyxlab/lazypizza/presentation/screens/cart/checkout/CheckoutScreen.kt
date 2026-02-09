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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.navigation.AuthScreenDestination
import com.tonyxlab.lazypizza.navigation.MenuScreenDestination
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarFour
import com.tonyxlab.lazypizza.presentation.core.components.CartItemActions
import com.tonyxlab.lazypizza.presentation.core.components.EmptyScreenContent
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.CheckoutFormContent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components.OrderConfirmationContent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutActionEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutStep
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.menuItemsMocks
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

                    CheckoutActionEvent.ExitCheckout -> navigator.navigate(
                            MenuScreenDestination
                    )

                    CheckoutActionEvent.NavigateToAuthScreen -> navigator.navigate(
                            AuthScreenDestination
                    )
                }
            },
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

    when (uiState.checkoutStep) {

        CheckoutStep.STEP_CHECKOUT -> {

            if (uiState.isAuthenticated) {

                CheckoutFormContent(
                        uiState = uiState,
                        onEvent = onEvent,
                        cartItemActions = cartItemActions,
                        isDeviceWide = isDeviceWide,
                        modifier = modifier
                )
            } else {
                Column (modifier = Modifier.padding(all = MaterialTheme.spacing.spaceMedium)){

                    AppTopBarFour(
                            onClick = { onEvent(CheckoutUiEvent.ExitCheckout) })
                    EmptyScreenContent(
                            modifier = Modifier.padding(top = MaterialTheme.spacing.spaceTwelve * 10),
                            title = stringResource(id = R.string.cap_text_not_signed_in),
                            subTitle = stringResource(id = R.string.cap_text_login_for_checkout),
                            buttonText = stringResource(id = R.string.btn_text_sign_in),
                            onEvent = { onEvent(CheckoutUiEvent.SignIn) }
                    )
                }
            }
        }

        CheckoutStep.STEP_CONFIRMATION -> {
            OrderConfirmationContent(
                    uiState = uiState,
                    onEvent = onEvent,
                    isDeviceWide = isDeviceWide,
                    modifier = modifier
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CheckoutScreenContent_Preview() {
    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            CheckoutScreenContent(
                    modifier = Modifier
                            .fillMaxSize(),
                    uiState = CheckoutUiState(
                            menuItems = menuItemsMocks,
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



