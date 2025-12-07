package com.tonyxlab.lazypizza.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.PrimaryGradientEnd
import com.tonyxlab.lazypizza.presentation.theme.Title4

@Composable
fun BottomNavBar(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    itemCount: Int = 0
) {

    val navigationState = navigator.state
    NavigationBar(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {

        NavigationBarItem(
                modifier = Modifier.animateContentSize(),
                selected = navigationState.topLevelRoute == MenuScreenDestination,
                onClick = { navigator.navigate(MenuScreenDestination) },
                icon = {

                    NavItemIcon(
                            selected = navigationState.topLevelRoute == MenuScreenDestination,
                            painterRes = R.drawable.icon_menu,
                            contentDescription = "Menu"
                    )
                },
                label = { Text(text = "Menu") },
                colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary
                )
        )

        NavigationBarItem(
                modifier = Modifier.animateContentSize(),
                selected = navigationState.topLevelRoute == CartScreenDestination,
                onClick = { navigator.navigate(CartScreenDestination) },
                icon = {

                    NavItemIcon(
                            selected = navigationState.topLevelRoute == CartScreenDestination,
                            painterRes = R.drawable.icon_cart,
                            contentDescription = "Cart",
                            hasBadge = true,
                            itemCount = itemCount
                    )

                },
                label = { Text(text = "Cart") },
                colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary
                ),

                )

        NavigationBarItem(
                modifier = Modifier.animateContentSize(),
                selected = navigationState.topLevelRoute == HistoryScreenDestination,
                onClick = { navigator.navigate(HistoryScreenDestination) },
                icon = {
                    NavItemIcon(
                            selected = navigationState.topLevelRoute == HistoryScreenDestination,
                            painterRes = R.drawable.icon_history,
                            contentDescription = "History"
                    )
                },
                label = { Text(text = "History") },
                colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary
                )
        )
    }
}

@Composable
private fun NavItemIcon(
    selected: Boolean,
    @DrawableRes
    painterRes: Int,
    modifier: Modifier = Modifier,
    hasBadge: Boolean = false,
    itemCount: Int = 0,
    contentDescription: String = ""
) {
    val containerSize = MaterialTheme.spacing.spaceLarge


    val badgeSize =
        if (itemCount > 9)
            MaterialTheme.spacing.spaceTen * 2
        else
            MaterialTheme.spacing.spaceDoubleDp * 9

    Box(
            modifier = modifier.size(containerSize),
            contentAlignment = Alignment.Center
    ) {

        if (selected) {
            Box(
                    modifier = Modifier
                            .matchParentSize()
                            .clip(CircleShape)
                            .background(PrimaryGradientEnd.copy(alpha = .08f))
            )
        }

        Icon(
                painter = painterResource(painterRes),
                contentDescription = contentDescription,
                tint = if (selected) MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (hasBadge && itemCount > 0) {
            Box(
                    modifier = Modifier
                            .size(badgeSize)
                            .align(Alignment.TopEnd)
                            .offset(x = badgeSize / 3, y = (-badgeSize / 3))
                            .clip(CircleShape)
                            .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                            ).animateContentSize(),
                    contentAlignment = Alignment.Center

            ) {
                Text(
                        text = stringResource(
                                id = R.string.cap_text_item_count,
                                itemCount
                        ),
                        style = MaterialTheme.typography.Title4.copy(
                                color = MaterialTheme.colorScheme.onPrimary
                        )
                )
            }
        }
    }
}

@Composable
fun AppNavigationRail(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    itemCount: Int = 0
) {

    val navigationState = navigator.state

    NavigationRail(modifier = modifier, containerColor = MaterialTheme.colorScheme.surface) {
        Spacer(modifier = Modifier.weight(1f))
        NavigationRailItem(
                modifier = Modifier.animateContentSize(),
                selected = navigationState.topLevelRoute == MenuScreenDestination,
                onClick = { navigator.navigate(MenuScreenDestination) },
                icon = {

                    NavItemIcon(
                            selected = navigationState.topLevelRoute == MenuScreenDestination,
                            painterRes = R.drawable.icon_menu,
                            contentDescription = "Menu"
                    )
                },
                label = { Text(text = "Menu") },
                colors = NavigationRailItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
        )

        NavigationRailItem(
                modifier = Modifier.animateContentSize(),
                selected = navigationState.topLevelRoute == CartScreenDestination,
                onClick = { navigator.navigate(CartScreenDestination) },
                icon = {

                    NavItemIcon(
                            selected = navigationState.topLevelRoute == CartScreenDestination,
                            painterRes = R.drawable.icon_cart,
                            contentDescription = "Cart",
                            hasBadge = true,
                            itemCount = itemCount
                    )

                },
                label = { Text(text = "Cart") },
                colors = NavigationRailItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
        )


        NavigationRailItem(
                modifier = Modifier.animateContentSize(),
                selected = navigationState.topLevelRoute == HistoryScreenDestination,
                onClick = { navigator.navigate(HistoryScreenDestination) },
                icon = {
                    NavItemIcon(
                            selected = navigationState.topLevelRoute == HistoryScreenDestination,
                            painterRes = R.drawable.icon_history,
                            contentDescription = "History"
                    )
                },
                label = { Text(text = "History") },
                colors = NavigationRailItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.weight(1f))
    }

}

@PreviewLightDark
@Composable
private fun NavItemIcon_Preview() {

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            NavItemIcon(selected = true, R.drawable.icon_cart, hasBadge = true, itemCount = 7)
            NavItemIcon(selected = true, R.drawable.icon_cart, hasBadge = true, itemCount = 13)
            NavItemIcon(selected = true, R.drawable.icon_menu, hasBadge = false)
            NavItemIcon(selected = false, R.drawable.icon_history, hasBadge = false)
        }
    }
}




