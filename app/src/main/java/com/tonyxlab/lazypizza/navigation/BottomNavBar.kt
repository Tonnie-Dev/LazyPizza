package com.tonyxlab.lazypizza.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.PrimaryGradientEnd

@Composable
fun BottomNavBar(
    navigator: Navigator,
    modifier: Modifier = Modifier
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
                            contentDescription = "Cart"
                    )

                },
                label = { Text(text = "Cart") },
                colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary
                )
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
    contentDescription: String = ""
) {

    if (selected) {
        Box(
                modifier = modifier
                        .clip(CircleShape)

                        .background(PrimaryGradientEnd.copy(alpha = .08f))
                        .size(MaterialTheme.spacing.spaceLarge)
                        .padding(MaterialTheme.spacing.spaceDoubleDp * 3),
                contentAlignment = Alignment.Center
        ) {

            Icon(
                    painter = painterResource(painterRes),
                    contentDescription = contentDescription
            )

        }
    } else {

        Icon(
                painter = painterResource(painterRes),
                contentDescription = contentDescription
        )
    }
}

@Composable
fun AppNavigationRail(
    navigator: Navigator,
    modifier: Modifier = Modifier
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
                            contentDescription = "Cart"
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



