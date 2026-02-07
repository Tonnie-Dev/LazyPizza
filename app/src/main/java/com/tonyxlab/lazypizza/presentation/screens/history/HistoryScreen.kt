@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.history

import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.navigation.AppNavigationRail
import com.tonyxlab.lazypizza.navigation.AuthScreenDestination
import com.tonyxlab.lazypizza.navigation.BottomNavBar
import com.tonyxlab.lazypizza.navigation.MenuScreenDestination
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarThree
import com.tonyxlab.lazypizza.presentation.core.components.EmptyScreenContent
import com.tonyxlab.lazypizza.presentation.core.components.LazyListComponent
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.history.components.OrderItemCard
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryActionEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.SetStatusBarIconsColor
import com.tonyxlab.lazypizza.utils.rememberIsDeviceWide
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier, viewModel: HistoryViewModel = koinViewModel()
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
                                titleText = stringResource(id = R.string.topbar_text_history)
                        )
                    },
                    actionEventHandler = { _, action ->

                        when (action) {
                            HistoryActionEvent.NavigateToAuth -> {
                                navigator.navigate(route = AuthScreenDestination)
                            }

                            HistoryActionEvent.NavigateToMenu -> {
                                navigator.navigate(MenuScreenDestination)
                            }
                        }
                    },
                    onBackPressed = { activity.finish() },
                    containerColor = MaterialTheme.colorScheme.background
            ) {
                HistoryScreenContent(
                        modifier = Modifier,
                        uiState = uiState,
                        onEvent = viewModel::onEvent
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
                            titleText = stringResource(id = R.string.topbar_text_history)
                    )
                },
                bottomBar = {
                    BottomNavBar(
                            navigator = navigator,
                            itemCount = it.badgeCount
                    )
                },
                onBackPressed = { activity.finish() },
                actionEventHandler = { _, action ->
                    when (action) {
                        HistoryActionEvent.NavigateToAuth -> {
                            navigator.navigate(route = AuthScreenDestination)
                        }

                        HistoryActionEvent.NavigateToMenu -> {
                            navigator.navigate(MenuScreenDestination)
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
        ) {
            HistoryScreenContent(
                    modifier = Modifier,
                    uiState = uiState,
                    onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
private fun HistoryScreenContent(
    uiState: HistoryUiState,
    onEvent: (HistoryUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isSignedIn) {

            uiState.orderItems.ifEmpty {
                EmptyScreenContent(
                        modifier = Modifier.padding(top = MaterialTheme.spacing.spaceTwelve * 10),
                        title = stringResource(id = R.string.cap_text_no_orders_yet),
                        subTitle = stringResource(id = R.string.cap_text_orders_will_appear_here),
                        buttonText = stringResource(id = R.string.btn_text_go_to_menu),
                        onEvent = { onEvent(HistoryUiEvent.GoToMenu) }
                )
            }

            LazyListComponent(items = uiState.orderItems, key = { it.id }) { order ->

                OrderItemCard(order = order)

            }
        } else {
            EmptyScreenContent(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.spaceTwelve * 10),
                    title = stringResource(id = R.string.cap_text_not_signed_in),
                    subTitle = stringResource(id = R.string.cap_text_please_sign_in),
                    buttonText = stringResource(id = R.string.btn_text_sign_in),
                    onEvent = { onEvent(HistoryUiEvent.SignIn) }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun HistoryScreenContent_Preview() {
    LazyPizzaTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            HistoryScreenContent(
                    uiState = HistoryUiState(),
                    onEvent = {}
            )
        }
    }
}

