
@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.tonyxlab.lazypizza.presentation.screens.home

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.navigation.AppNavigationRail
import com.tonyxlab.lazypizza.navigation.BottomNavBar
import com.tonyxlab.lazypizza.navigation.DetailScreenDestination
import com.tonyxlab.lazypizza.navigation.Navigator
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarOne
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.home.components.CategoryTabs
import com.tonyxlab.lazypizza.presentation.screens.home.components.LazyCategoryList
import com.tonyxlab.lazypizza.presentation.screens.home.components.PizzaCard
import com.tonyxlab.lazypizza.presentation.screens.home.components.SearchComponent
import com.tonyxlab.lazypizza.presentation.screens.home.components.SideItemCard
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.DeviceType
import com.tonyxlab.lazypizza.utils.SetStatusBarIconsColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    SetStatusBarIconsColor(darkIcons = true)

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity ?: return

    val windowClass = calculateWindowSizeClass(activity)
    val deviceType = DeviceType.fromWindowSizeClass(windowClass)

    val isDeviceWide = deviceType != DeviceType.MOBILE_PORTRAIT

    if (isDeviceWide) {
        // ✅ TABLET / FOLDABLE / DESKTOP LAYOUT
        Row(modifier = Modifier.fillMaxSize()) {

            // ✅ LEFT NAVIGATION RAIL
            AppNavigationRail(navigator)

            // ✅ MAIN CONTENT
            BaseContentLayout(
                    modifier = modifier.weight(1f),
                    viewModel = viewModel,
                    topBar = {
                        AppTopBarOne(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spaceMedium),
                                phoneNumber = uiState.phoneNumber,
                                onCallClick = {
                                    viewModel.onEvent(HomeUiEvent.PlaceCall)
                                }
                        )
                    },
                    bottomBar = {}, // ✅ NO BOTTOM BAR ON WIDE DEVICES
                    actionEventHandler = { _, action ->
                        when (action) {
                            HomeActionEvent.LaunchDialingPad -> {
                                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                    data = "tel:${uiState.phoneNumber}".toUri()
                                }
                                context.startActivity(dialIntent)
                            }

                            is HomeActionEvent.NavigateToDetailsScreen -> {
                                navigator.navigate(DetailScreenDestination(id = action.id))
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background
            ) { state ->
                HomeScreenContent(
                        modifier = modifier,
                        uiState = state,
                        onEvent = viewModel::onEvent,
                        isDeviceWide = true
                )
            }
        }

    } else {
        // ✅ PHONE LAYOUT
        BaseContentLayout(
                modifier = modifier,
                viewModel = viewModel,
                topBar = {
                    AppTopBarOne(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spaceMedium),
                            phoneNumber = uiState.phoneNumber,
                            onCallClick = {
                                viewModel.onEvent(HomeUiEvent.PlaceCall)
                            }
                    )
                },
                bottomBar = {
                    BottomNavBar(navigator = navigator) // ✅ ONLY HERE
                },
                actionEventHandler = { _, action ->
                    when (action) {
                        HomeActionEvent.LaunchDialingPad -> {
                            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                data = "tel:${uiState.phoneNumber}".toUri()
                            }
                            context.startActivity(dialIntent)
                        }

                        is HomeActionEvent.NavigateToDetailsScreen -> {
                            navigator.navigate(DetailScreenDestination(id = action.id))
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
        ) { state ->
            HomeScreenContent(
                    modifier = modifier,
                    uiState = state,
                    onEvent = viewModel::onEvent,
                    isDeviceWide = false
            )
        }
    }
}


@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    isDeviceWide: Boolean,
    modifier: Modifier = Modifier
) {

    val pizzasList = uiState.allPizzaItems
    val sideItemsList = uiState.filteredSideItems
    val header = uiState.selectedCategory.categoryName

    val context = LocalContext.current



    if (isDeviceWide) {
        Column(
                modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                        .animateContentSize(
                                animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessLow
                                )
                        )
                        .background(MaterialTheme.colorScheme.background)
        ) {

            Image(
                    modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .fillMaxWidth(),
                    painter = painterResource(R.drawable.banner_big),
                    contentDescription = stringResource(R.string.cds_text_banner),
                    contentScale = ContentScale.Crop
            )

            SearchComponent(
                    modifier = Modifier,
                    uiState = uiState
            )

            AnimatedVisibility(visible = !uiState.textFieldState.text.isNotBlank()) {

                Column {
                    CategoryTabs(
                            modifier = Modifier,
                            categories = Category.entries,
                            selectedCategory = uiState.selectedCategory,
                            onEvent = onEvent
                    )


                    when {

                        uiState.selectedCategory == Category.PIZZA -> {

                            LazyCategoryList(
                                    header = header,
                                    items = pizzasList,
                                    key = { item -> item.id },
                                    isDeviceWide = true
                            ) { pizza ->

                                PizzaCard(
                                        pizza = pizza,
                                        onEvent = onEvent
                                )

                            }
                        }

                        else -> {

                            LazyCategoryList(
                                    header = header,
                                    items = sideItemsList,
                                    key = { item -> item.id },
                                    isDeviceWide = true
                            ) { sideItem ->

                                SideItemCard(
                                        sideItem = sideItem,
                                        uiState = uiState,
                                        onEvent = onEvent
                                )

                            }

                        }
                    }
                }
            }
        }
    } else {
        Column(
                modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                        .animateContentSize(
                                animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessLow
                                )
                        )
                        .background(MaterialTheme.colorScheme.background)
        ) {

            Image(
                    modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .fillMaxWidth(),
                    painter = painterResource(R.drawable.banner_small),
                    contentDescription = stringResource(R.string.cds_text_banner),
                    contentScale = ContentScale.Crop
            )

            SearchComponent(
                    modifier = Modifier.padding(top = 0.dp),
                    uiState = uiState
            )

            AnimatedVisibility(visible = !uiState.textFieldState.text.isNotBlank()) {

                Column {
                    CategoryTabs(
                            modifier = Modifier,
                            categories = Category.entries,
                            selectedCategory = uiState.selectedCategory,
                            onEvent = onEvent
                    )

                    if (uiState.selectedCategory == Category.PIZZA) {
                        LazyCategoryList(
                                header = header,
                                items = pizzasList,
                                key = { item -> item.id },
                                isDeviceWide = false
                        ) { pizzaItem ->
                            PizzaCard(
                                    pizza = pizzaItem,
                                    onEvent = onEvent
                            )
                        }

                    } else {

                        LazyCategoryList(
                                header = uiState.selectedCategory.categoryName,
                                items = uiState.filteredSideItems,
                                key = { item -> item.id },
                                isDeviceWide = false
                        ) { sideItem ->
                            SideItemCard(
                                    sideItem = sideItem,
                                    uiState = uiState,
                                    onEvent = onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenContent_Preview() {

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
        ) {
            HomeScreenContent(
                    uiState = HomeUiState(),
                    onEvent = {},
                    isDeviceWide = false
            )
        }
    }
}