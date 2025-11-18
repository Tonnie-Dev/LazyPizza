package com.tonyxlab.lazypizza.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.navigation.NavOperations
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarTwo
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.screens.details.components.StickyAddToCart
import com.tonyxlab.lazypizza.presentation.screens.details.components.ToppingsCardContent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(
    id: Long,
    navOperations: NavOperations,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(id) })
) {

    BaseContentLayout(
            modifier = modifier.fillMaxSize(),
            viewModel = viewModel,
            topBar = { AppTopBarTwo(onClick = { navOperations.popBackStack() }) },
            containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { uiState ->
        DetailsScreenContent(
                modifier = Modifier,
                uiState = uiState,
                onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun DetailsScreenContent(
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Box {
        Column {

            uiState.pizzaStateItem?.let {

                DisplayImage(
                        modifier = Modifier
                                .fillMaxWidth()
                                .weight(.35f),
                        imageSize = 300.dp,
                        imageUrl = it.imageUrl,
                )
            }

            ToppingsCardContent(
                    modifier = Modifier.weight(.65f),
                    uiState = uiState,
                    onEvent = onEvent
            )
        }

        StickyAddToCart(
                modifier = modifier.align(alignment = Alignment.BottomCenter),
                buttonText = stringResource(R.string.btn_text_add_to_cart, uiState.aggregatePrice),
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
