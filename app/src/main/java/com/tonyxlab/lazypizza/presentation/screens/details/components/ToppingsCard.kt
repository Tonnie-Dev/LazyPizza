package com.tonyxlab.lazypizza.presentation.screens.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.presentation.core.components.CounterItem
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiState
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold
import com.tonyxlab.lazypizza.presentation.theme.Title2
import com.tonyxlab.lazypizza.presentation.theme.TopLeftShape16
import com.tonyxlab.lazypizza.presentation.theme.ToppingCircleBackground
import com.tonyxlab.lazypizza.utils.toPrice

@Composable
fun ToppingsCardContent(
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
            modifier = modifier
                    .fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.TopLeftShape16,
            shadowElevation = 2.dp
    ) {
        Column(
                modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                        .padding(top = MaterialTheme.spacing.spaceTen * 2),
                verticalArrangement = Arrangement.spacedBy(
                        space = MaterialTheme.spacing.spaceMedium
                )
        ) {
            PizzaMetaData(
                    modifier = Modifier,
                    pizzaName = uiState.pizzaStateItem?.name ?: "",
                    ingredients = uiState.pizzaStateItem?.ingredients ?: emptyList()
            )

            ToppingsGrid(
                    modifier = Modifier,
                    uiState = uiState,
                    onEvent = onEvent
            )
        }
    }
}

@Composable
private fun ToppingsGrid(
    uiState: DetailsUiState,
    onEvent: (DetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
            modifier = modifier.fillMaxWidth(),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
    ) {

        stickyHeader {
            Box(
                    modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.spacing.spaceSmall)

            ) {
                Text(
                        text = stringResource(id = R.string.header_text_add_toppings),
                        style = MaterialTheme.typography.Label2SemiBold.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                )
            }
        }

        items(items = uiState.toppings, key = { it.id }) { item ->
            ToppingsCard(
                    topping = item,
                    selected = uiState.selectedToppings.isSelected(topping = item),
                    counter = uiState.selectedToppings.counterFor(topping = item),
                    onEvent = onEvent
            )
        }
    }
}

@Composable
private fun ToppingsCard(
    topping: Topping,
    counter: Int,
    selected: Boolean,
    onEvent: (DetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val haptics = LocalHapticFeedback.current
    val animatedBorderColor by animateColorAsState(
            targetValue = if (selected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.outline,
            label = "borderColor"
    )

    val animatedBorderWidth by animateDpAsState(
            targetValue = if (selected)
                MaterialTheme.spacing.spaceDoubleDp
            else
                MaterialTheme.spacing.spaceSingleDp,
            label = "borderWidth"
    )
    Card(
            modifier = modifier.border(
                    width = animatedBorderWidth,
                    color = animatedBorderColor,
                    shape = MaterialTheme.shapes.medium
            ),
            shape = MaterialTheme.shapes.medium,
            onClick = { onEvent(DetailsUiEvent.ClickToppingCard(topping)) },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.spaceTwelve),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall)
        ) {

            DisplayImage(
                    imageUrl = topping.imageUrl,
                    containerSize = MaterialTheme.spacing.spaceSmall * 8,
                    shape = CircleShape,
                    backgroundColor = ToppingCircleBackground,
                    padding = PaddingValues(MaterialTheme.spacing.spaceExtraSmall),
                    prefix = "topping_",
                    fallbackDrawableRes = R.drawable.topping_cheese,
                    errorDrawableRes = R.drawable.topping_bacon
            )

            Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                            MaterialTheme.spacing.spaceSmall
                    )
            ) {
                Text(
                        text = topping.toppingName,
                        style = MaterialTheme.typography.Body3Regular.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                )

                AnimatedContent(targetState = selected, label = "contentTransition") { isSelected ->

                    if (isSelected) {

                        CounterItem(
                                modifier = modifier,
                                onAdd = {
                                    onEvent(DetailsUiEvent.AddExtraToppings(topping = topping))
                                },
                                onRemove = {
                                    onEvent(DetailsUiEvent.RemoveExtraToppings(topping = topping))
                                },
                                counter = counter,
                                isCounterMaxed = (counter == 3)
                        )

                    } else {
                        Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                    text = topping.toppingPrice.toPrice(),
                                    style = MaterialTheme.typography.Title2.copy(
                                            color = MaterialTheme.colorScheme.onSurface,
                                            textAlign = TextAlign.Center,
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PizzaMetaData(
    pizzaName: String,
    ingredients: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                    space = MaterialTheme.spacing.spaceExtraSmall
            )
    ) {
        Text(
                text = pizzaName,
                style = MaterialTheme.typography.Title1SemiBold.copy(
                        color = MaterialTheme.colorScheme.onSurface
                )
        )

        Text(
                text = ingredients.joinToString(),
                style = MaterialTheme.typography.Body3Regular.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        )
    }
}

@PreviewLightDark
@Composable
private fun ToppingsCard_Preview() {

    val toppingOne = Topping(
            id = 1L,
            toppingName = "Bacon",
            toppingPrice = 1.0,
            imageUrl = "",
            counter = 1
    )

    val toppingTwo = Topping(

            id = 2L,
            toppingName = "Bacon",
            toppingPrice = .5,
            imageUrl = "",
            counter = 1
    )

    LazyPizzaTheme {

        Row(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
                        .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(
                        space = MaterialTheme.spacing.spaceMedium
                )
        ) {

            ToppingsCard(
                    modifier = Modifier.weight(1f),
                    topping = toppingOne,
                    selected = false,
                    counter = 3,
                    onEvent = {}
            )

            ToppingsCard(
                    modifier = Modifier.weight(1f),
                    topping = toppingOne,
                    counter = 2,
                    selected = true,
                    onEvent = {}
            )

            ToppingsCard(
                    modifier = Modifier.weight(1f),
                    counter = 1,
                    topping = toppingTwo,
                    selected = false,
                    onEvent = {}
            )
        }
    }
}

private fun Set<Topping>.isSelected(topping: Topping): Boolean {
    return this.any { it.id == topping.id }
}

private fun Set<Topping>.counterFor(topping: Topping): Int =
    firstOrNull { it.id == topping.id }?.counter ?: 0


