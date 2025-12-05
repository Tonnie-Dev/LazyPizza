@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tonyxlab.lazypizza.presentation.screens.cart

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.navigation.AppNavigationRail
import com.tonyxlab.lazypizza.navigation.BottomNavBar
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppButton
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarThree
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold
import com.tonyxlab.lazypizza.utils.DeviceType
import com.tonyxlab.lazypizza.utils.SetStatusBarIconsColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier, viewModel: CartViewModel = koinViewModel()
) {

    SetStatusBarIconsColor(darkIcons = true)

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = LocalActivity.current ?: return

    val windowClass = calculateWindowSizeClass(activity)
    val deviceType = DeviceType.fromWindowSizeClass(windowClass)

    val isDeviceWide = deviceType != DeviceType.MOBILE_PORTRAIT

    if (isDeviceWide) {

        Row(modifier = Modifier.fillMaxSize()) {

            AppNavigationRail(navigator = navigator)
            BaseContentLayout(
                    modifier = modifier,
                    viewModel = viewModel,
                    topBar = {
                        AppTopBarThree(
                                modifier = Modifier,
                                titleText = stringResource(id = R.string.topbar_text_cart)
                        )
                    },
                    actionEventHandler = { _, action ->

                    },
                    onBackPressed = { activity.finish()},
                    containerColor = MaterialTheme.colorScheme.background
            ) {

                CartScreenContent()
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
                    BottomNavBar(navigator = navigator)
                },
                actionEventHandler = { _, action ->

                },
                onBackPressed = { activity.finish()},
                containerColor = MaterialTheme.colorScheme.background
        ) {
            CartScreenContent()
        }
    }
}

@Composable
private fun CartScreenContent(modifier: Modifier = Modifier) {

    Box(
            modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
    ) {

        EmptyCartBody()
    }

}

@Composable
private fun EmptyCartBody(modifier: Modifier = Modifier) {

    Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceTen * 2),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3)
        ) {

            Text(
                    text = stringResource(id = R.string.cap_text_empty_cart),
                    style = MaterialTheme.typography.Title1SemiBold.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                    )
            )

            Text(
                    text = stringResource(id = R.string.cap_text_head_back),
                    style = MaterialTheme.typography.Body3Regular.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                    )
            )
        }

        AppButton(
                onClick = {},
                buttonText = stringResource(id = R.string.btn_text_back_to_menu)
        )
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenContent_Preview() {

    LazyPizzaTheme {
        CartScreenContent()
    }
}

