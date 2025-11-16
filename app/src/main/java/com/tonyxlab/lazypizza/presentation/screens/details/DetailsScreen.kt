package com.tonyxlab.lazypizza.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.screens.details.components.ToppingsCardContent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiState
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title2
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(
    id: Long,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(id) })
) {

    BaseContentLayout(
           modifier = modifier.fillMaxSize(),
            viewModel = viewModel) { uiState ->

        Text(
                modifier = Modifier.align(Alignment.Center),
                text = "id is: $id",
                style = MaterialTheme.typography.Title2
        )
        /* DetailsScreenContent(
                 modifier = modifier,
                 uiState = uiState,
                 onEvent = viewModel::onEvent
         )*/
    }
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

        DisplayImage(
                modifier = Modifier
                        .fillMaxWidth()
                        .weight(.4f),
                imageUrl = uiState.pizzaItem.imageUrl,
        )

        ToppingsCardContent(
                modifier = Modifier.weight(.6f),
                uiState = uiState,
                onEvent = onEvent
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
