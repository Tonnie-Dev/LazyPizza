@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tonyxlab.lazypizza.presentation.screens.details

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarTwo
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.details.components.PizzaMetaData
import com.tonyxlab.lazypizza.presentation.screens.details.components.StickyAddToCart
import com.tonyxlab.lazypizza.presentation.screens.details.components.ToppingsCardContent
import com.tonyxlab.lazypizza.presentation.screens.details.components.ToppingsGrid
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsActionEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.VerticalRoundedCornerShape16
import com.tonyxlab.lazypizza.utils.DeviceType
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(
    id: Long,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(id) })
) {
    BaseContentLayout(
            modifier = modifier.fillMaxSize(),
            viewModel = viewModel,
            topBar = { AppTopBarTwo(onClick = { navigator.goBack() }) },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            actionEventHandler = { _, action ->

                when (action) {
                    DetailsActionEvent.NavigateBackToMenu -> navigator.goBack()
                }
            }
    ) { uiState ->
        DetailsScreenContent(
                modifier = Modifier,
                uiState = uiState,
                onEvent = viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun DetailsScreenContent(
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val activity = context as? Activity ?: return

    val windowClass = calculateWindowSizeClass(activity = activity)
    val deviceType = DeviceType.fromWindowSizeClass(windowSizeClass = windowClass)

    val isDeviceWide = when (deviceType) {
        DeviceType.MOBILE_PORTRAIT -> false
        else -> true
    }

    when {
        isDeviceWide -> {

            Row {
                Column(modifier = Modifier.weight(1f)) {

                    uiState.pizzaStateItem?.let {

                        DisplayImage(
                                modifier = Modifier
                                        .fillMaxWidth(),
                                imageSize = 300.dp,
                                imageUrl = it.imageUrl,
                        )

                        PizzaMetaData(
                                modifier = Modifier
                                        .weight(1f)
                                        .padding(start = MaterialTheme.spacing.spaceMedium),
                                pizzaName = it.name,
                                ingredients = it.ingredients
                        )
                    }
                }

                Box(
                        modifier = Modifier
                                .clip(MaterialTheme.shapes.VerticalRoundedCornerShape16)
                                .background(MaterialTheme.colorScheme.surface)
                                .weight(1f)
                                .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                )

                {
                    ToppingsGrid(
                            modifier = Modifier

                                    .padding(MaterialTheme.spacing.spaceMedium),
                            uiState = uiState,
                            onEvent = onEvent
                    )

                    StickyAddToCart(
                            modifier = modifier.align(alignment = Alignment.BottomCenter),
                            buttonText = stringResource(
                                    R.string.btn_text_add_to_cart_with_price,
                                    uiState.aggregatePrice
                            ),
                            onEvent = onEvent
                    )
                }
            }
        }

        else -> {

            Box {
                Column {

                    uiState.pizzaStateItem?.let {
                        DisplayImage(
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(.35f),
                                imageSize = 300.dp,
                                imageUrl = it.imageUrl,
                        )
                    }

                    ToppingsCardContent(
                            modifier = Modifier.weight(.65f),
                            uiState = uiState,
                            onEvent = onEvent
                    )
                }

                StickyAddToCart(
                        modifier = modifier.align(alignment = Alignment.BottomCenter),
                        buttonText = stringResource(
                                R.string.btn_text_add_to_cart_with_price,
                                uiState.aggregatePrice
                        ),
                        onEvent = onEvent
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DetailsScreenContent_Preview() {

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
        ) {
            DetailsScreenContent(
                    uiState = DetailsUiState(),
                    onEvent = {}
            )
        }
    }
}
