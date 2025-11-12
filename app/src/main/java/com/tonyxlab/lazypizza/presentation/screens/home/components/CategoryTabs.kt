package com.tonyxlab.lazypizza.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.domain.model.Category
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme

@Composable
fun CategoryTabs(
    categories: List<Category>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onEvent: (HomeUiEvent) -> Unit
) {
    Row(
            modifier = modifier.fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.spaceTwelve),
            horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.spaceSmall),
            verticalAlignment = Alignment.CenterVertically
    ) {

        categories.forEachIndexed { i, category ->
            FilterChip(
                    selected = i == selectedIndex,
                    label = {
                        Text(
                                text = category.categoryName,
                                style = MaterialTheme.typography.Label2SemiBold.copy(
                                        color = MaterialTheme.colorScheme.onSurface
                                )
                        )
                    },
                    onClick = { onEvent(HomeUiEvent.SelectCategoryTab(position = i)) }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CategoryTabs_Preview() {
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {

            CategoryTabs(
                    modifier = Modifier.fillMaxWidth(),
                    categories = Category.entries,
                    selectedIndex = 0
            ) { }
        }
    }
}