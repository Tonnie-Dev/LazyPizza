package com.tonyxlab.lazypizza.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.Pizza
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.lazypizza.presentation.theme.Body1Medium
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold
import com.tonyxlab.lazypizza.presentation.theme.VerticalRoundedCornerShape12
import com.tonyxlab.lazypizza.utils.mockPizzas

@Composable
fun PizzaCard(
    pizza: Pizza,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier
                    .fillMaxWidth()
                    .border(
                            width = MaterialTheme.spacing.spaceSingleDp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                    ),
            onClick = { onEvent(HomeUiEvent.ClickPizza) },
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
        ) {

            DisplayImage(
                    imageUrl = pizza.imageUrl,
                    size = MaterialTheme.spacing.spaceTwelve * 10,
                    shape = MaterialTheme.shapes.VerticalRoundedCornerShape12,
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Column(
                    modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                            .padding(vertical = MaterialTheme.spacing.spaceTwelve),
                    verticalArrangement = Arrangement.spacedBy(
                            space = MaterialTheme.spacing.spaceTen
                    )
            ) {

                Column {

                    Text(
                            text = pizza.name, style = MaterialTheme.typography.Body1Medium
                            .copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                     fontWeight = FontWeight.W600
                            )
                    )

                    Text(
                            text = pizza.ingredients.joinToString(),
                            style = MaterialTheme.typography.Body3Regular.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            maxLines = 2
                    )
                }

                Text(
                        text = stringResource(
                                id = R.string.dollar_price_tag, pizza.price
                        ),
                        style = MaterialTheme.typography.Title1SemiBold.copy(
                                color = MaterialTheme.colorScheme.onSurface
                        ),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PizzaCard_Preview() {

    val pizzas = mockPizzas

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {

            LazyCategoryList(
                    header = "Pizza", items = pizzas,
                    key = { it.id }
            ) { item ->

                PizzaCard(
                        modifier = Modifier,
                        pizza = item,
                        onEvent = {}
                )
            }
        }
    }
}


