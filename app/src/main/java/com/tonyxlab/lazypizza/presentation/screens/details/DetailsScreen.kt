package com.tonyxlab.lazypizza.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarTwo
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.screens.details.components.PizzaMetaData
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme

@Composable
fun DetailsScreen(modifier: Modifier = Modifier) {

}

@Composable
private fun DetailsScreenContent(
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()

    ) {

        AppTopBarTwo()
        DisplayImage(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = uiState.pizzaItem.imageUrl,
        )

        PizzaMetaData(
                pizzaName = uiState.pizzaItem.name,
                ingredients = uiState.pizzaItem.ingredients
        )
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
