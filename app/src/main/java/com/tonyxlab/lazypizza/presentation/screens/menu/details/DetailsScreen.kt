@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tonyxlab.lazypizza.presentation.screens.menu.details

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.fullImageUrl
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppSnackbarHost
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarTwo
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.core.components.ShowAppSnackbar
import com.tonyxlab.lazypizza.presentation.core.components.rememberSnackbarController
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.menu.details.components.PizzaMetaData
import com.tonyxlab.lazypizza.presentation.screens.menu.details.components.StickyAddToCart
import com.tonyxlab.lazypizza.presentation.screens.menu.details.components.ToppingsCardContent
import com.tonyxlab.lazypizza.presentation.screens.menu.details.components.ToppingsGrid
import com.tonyxlab.lazypizza.presentation.screens.menu.details.handling.DetailsActionEvent
import com.tonyxlab.lazypizza.presentation.screens.menu.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.menu.details.handling.DetailsUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.VerticalRoundedCornerShape16
import com.tonyxlab.lazypizza.utils.rememberIsDeviceWide
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

@Composable
fun DetailsScreen(
    id: Long,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(id) })
) {

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val snackbarController =
        rememberSnackbarController<DetailsUiEvent>()

    val snackbarHostState = remember { SnackbarHostState() }

    ShowAppSnackbar(
            triggerId = snackbarController.triggerId,
            snackbarHostState = snackbarHostState,
            message = snackbarController.message,
            actionLabel = snackbarController.actionLabel,
            onActionClick = {
                snackbarController.actionEvent?.let {
                    viewModel.onEvent(it)
                }
            }
    )

    BaseContentLayout(
            modifier = modifier,
            viewModel = viewModel,
            topBar = { AppTopBarTwo(onClick = { navigator.goBack() }) },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            actionEventHandler = { _, action ->
                when (action) {
                    DetailsActionEvent.NavigateBackToMenu -> navigator.goBack()
                    is DetailsActionEvent.ShowSnackbar -> {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        snackbarController.showSnackbar(
                                message = context.getString(action.messageRes),
                                actionLabel = context.getString(action.actionLabelRes),
                                actionEvent = null
                        )
                    }
                }
            },
            snackbarHost = {
                AppSnackbarHost(
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceTen * 8),
                        snackbarHostState = snackbarHostState
                )
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
    val isDeviceWide = rememberIsDeviceWide()

    var hasAnimated by remember { mutableStateOf(false) }

    val animatedAggregate by animateFloatAsState(
            targetValue = uiState.aggregatePrice.toFloat(),
            animationSpec = if (hasAnimated) {
                tween(
                        durationMillis = 450,
                        easing = FastOutSlowInEasing
                )
            } else {
                snap()
            },
            label = "CheckoutTotalAnimation",
            finishedListener = { hasAnimated = true }
    )

    if (isDeviceWide) {

        Row {
            Column(modifier = modifier.weight(1f)) {

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
                                .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                                .padding(top = MaterialTheme.spacing.spaceTen * 2)
                                .padding(bottom = MaterialTheme.spacing.spaceOneHundred),
                        uiState = uiState,
                        onEvent = onEvent
                )

                StickyAddToCart(
                        modifier = Modifier.align(alignment = Alignment.BottomCenter),
                        buttonText = stringResource(
                                R.string.btn_text_add_to_cart_with_price,
                                animatedAggregate
                        ),
                        onEvent = onEvent
                )
            }
        }

    } else {

        Box(modifier = modifier.fillMaxSize()) {

            Column(
                    modifier = Modifier
                            .fillMaxWidth()

            ) {

                uiState.pizzaStateItem?.let {
                    DisplayImage(
                            modifier = Modifier
                                    .fillMaxWidth(),
                            imageSize = 300.dp,
                            imageUrl = it.imageUrl
                    )
                }

                ToppingsCardContent(
                        modifier = Modifier
                                .padding(bottom = MaterialTheme.spacing.spaceOneHundred)
                                .navigationBarsPadding(),
                        uiState = uiState,
                        onEvent = onEvent
                )
            }

            StickyAddToCart(
                    modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .navigationBarsPadding(),
                    buttonText = stringResource(
                            R.string.btn_text_add_to_cart_with_price,
                            animatedAggregate
                    ),
                    onEvent = onEvent
            )
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
