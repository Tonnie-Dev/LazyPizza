package com.tonyxlab.lazypizza.presentation.screens.cart.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarFour
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
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
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBarFour(onClick = { onEvent(CheckoutUiEvent.GoBack) })
    }
}




