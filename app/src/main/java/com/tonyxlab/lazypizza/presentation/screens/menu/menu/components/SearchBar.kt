package com.tonyxlab.lazypizza.presentation.screens.menu.menu.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.components.AppInputField
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.menu.menu.handling.MenuUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.InvisibleSpacer
import com.tonyxlab.lazypizza.utils.mockPizzas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(
    uiState: MenuUiState,
    modifier: Modifier = Modifier
) {
    val textFieldHasText = uiState.textFieldState.text.isNotBlank()

    SearchBar(
            modifier = modifier.wrapContentHeight(),
            inputField = {
                AppInputField(
                        textFieldState = uiState.textFieldState,
                        placeholderText = stringResource(R.string.placeholder_text_search),
                        leadingIcon ={
                            Column {
                                InvisibleSpacer(componentSize = IntSize(0, 20))
                                Image(
                                        painter = painterResource(R.drawable.icon_search),
                                        contentDescription = stringResource(id = R.string.cds_text_search_icon),
                                )
                            }
                        }
                )
            },
            expanded = uiState.textFieldState.text.isNotBlank(),
            onExpandedChange = {},
            colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    dividerColor = Color.Transparent
            ),
    ) {

        LazyColumn(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.spaceSmall)
        ) {

            val items = uiState.searchResults

            if (textFieldHasText && items.isEmpty()) {
                item {
                    Text(
                            modifier = Modifier.padding(MaterialTheme.spacing.spaceSmall),
                            text = stringResource(id = R.string.cap_text_no_results),
                            style = MaterialTheme.typography.titleSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    )
                }
            }

            items(items = items, key = { it.id }) { item ->
                Text(
                        modifier = Modifier.padding(MaterialTheme.spacing.spaceSmall),
                        text = item.name,
                        style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface
                        )
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun SearchComponent_Preview() {

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            SearchComponent(
                    uiState = MenuUiState(pizzaCatalog = mockPizzas)
            )
        }
    }
}



