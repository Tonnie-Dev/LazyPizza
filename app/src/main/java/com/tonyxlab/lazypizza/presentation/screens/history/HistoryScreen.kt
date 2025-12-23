@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tonyxlab.lazypizza.presentation.screens.history

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.navigation.AppNavigationRail
import com.tonyxlab.lazypizza.navigation.AuthScreenDestination
import com.tonyxlab.lazypizza.navigation.BottomNavBar
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarThree
import com.tonyxlab.lazypizza.presentation.core.components.EmptyScreenContent
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryActionEvent
import com.tonyxlab.lazypizza.presentation.screens.history.handling.HistoryUiEvent
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.DeviceType
import com.tonyxlab.lazypizza.utils.SetStatusBarIconsColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier, viewModel: HistoryViewModel = koinViewModel()
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
                                titleText = stringResource(id = R.string.topbar_text_history)
                        )
                    },
                    actionEventHandler = { _, action ->

                        when(action){
                            HistoryActionEvent.NavigateToAuth -> navigator.navigate(
                                    AuthScreenDestination)
                        }
                    },
                    onBackPressed = { activity.finish() },
                    containerColor = MaterialTheme.colorScheme.background
            ) {
                HistoryScreenContent(
                        modifier = Modifier,
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
                    when(action){
                        HistoryActionEvent.NavigateToAuth -> navigator.navigate(
                                AuthScreenDestination)
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
        ) {
            HistoryScreenContent(
                    modifier = Modifier,
                    onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
private fun HistoryScreenContent(
    onEvent: (HistoryUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EmptyScreenContent(
                modifier = Modifier.padding(top = MaterialTheme.spacing.spaceTwelve * 10),
                title = stringResource(id = R.string.cap_text_not_signed_in),
                subTitle = stringResource(id = R.string.cap_text_please_sign_in),
                buttonText = stringResource(id = R.string.btn_text_sign_in),
                onEvent = { onEvent(HistoryUiEvent.SignIn) }
        )

    }
}

@PreviewLightDark
@Composable
private fun HistoryScreenContent_Preview() {
    LazyPizzaTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            HistoryScreenContent(onEvent = {})
        }
    }
}

