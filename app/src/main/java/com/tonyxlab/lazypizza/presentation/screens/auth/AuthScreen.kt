@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tonyxlab.lazypizza.presentation.screens.auth

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.auth.components.OtpInputSection
import com.tonyxlab.lazypizza.presentation.screens.auth.components.PhoneInputSection
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthActionEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.DeviceType
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseContentLayout(
            modifier = modifier,
            viewModel = viewModel,
            actionEventHandler = { _, action ->
                when (action) {
                    AuthActionEvent.NavigateBackToMenu -> navigator.goBack()
                    AuthActionEvent.NavigateToOtp -> {}
                }
            }
    ) {
        AuthScreenContent(
                uiState = uiState,
                onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun AuthScreenContent(
    uiState: AuthUiState,
    onEvent: (AuthUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val activity = LocalActivity.current ?: return
    val windowClass = calculateWindowSizeClass(activity)
    val deviceType = DeviceType.fromWindowSizeClass(windowClass)

    val isDeviceWide = deviceType != DeviceType.MOBILE_PORTRAIT

    val maxWidth = if (isDeviceWide)
        MaterialTheme.spacing.spaceTwoHundred * 2
    else
        Dp.Unspecified
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState.authScreenStep) {

            AuthUiState.AuthScreenStep.PhoneInputStep -> {

                PhoneInputSection(
                        modifier = Modifier
                                .align(alignment = Alignment.TopCenter)
                                .widthIn(max = maxWidth)
                                .padding(top = MaterialTheme.spacing.spaceTwoHundred),
                        uiState = uiState,
                        onEvent = onEvent
                )
            }

            AuthUiState.AuthScreenStep.OtpInputStep -> {

                OtpInputSection(
                        modifier = Modifier
                                .align(alignment = Alignment.TopCenter)
                                .widthIn(max = maxWidth)
                                .padding(top = MaterialTheme.spacing.spaceTwoHundred),
                        uiState = uiState,
                        onEvent = onEvent,
                        activity = activity
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AuthScreenContent_Preview() {

    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)

        ) {

            AuthScreenContent(
                    uiState = AuthUiState(),
                    onEvent = {}
            )
        }
    }
}