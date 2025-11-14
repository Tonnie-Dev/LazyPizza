package com.tonyxlab.lazypizza.presentation.screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.Topping
import com.tonyxlab.lazypizza.presentation.core.components.DisplayImage
import com.tonyxlab.lazypizza.presentation.core.components.getDrawableResId
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold
import com.tonyxlab.lazypizza.presentation.theme.Title2
import com.tonyxlab.lazypizza.presentation.theme.ToppingCircleBackground
import com.tonyxlab.lazypizza.presentation.theme.VerticalRoundedCornerShape12

@Composable
fun ToppingsCard(
    topping: Topping,
    selected: Boolean,
    onEvent: (DetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    Card(
            modifier = modifier.border(
                    width = MaterialTheme.spacing.spaceSingleDp,
                    shape = MaterialTheme.shapes.medium,
                    color = if (selected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline
            ),
            shape = MaterialTheme.shapes.medium,
            onClick = { onEvent(DetailsUiEvent.ClickToppingCard) },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.spaceTwelve),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                        MaterialTheme.spacing.spaceLarge
                )
        ) {

            Column( horizontalAlignment = Alignment.CenterHorizontally,) {
                DisplayImage(
                        imageUrl = topping.imageUrl,
                        size = MaterialTheme.spacing.spaceSmall * 8,
                        shape = CircleShape,
                        backgroundColor = ToppingCircleBackground,
                        padding = PaddingValues(MaterialTheme.spacing.spaceExtraSmall)
                )
               /* DisplayImage(
                        imageUrl = "",
                        modifier =,
                        size = Dp(),
                        shape =,
                        backgroundColor = Color(),
                        contentDescription = ""
                )
                AsyncImage(
                        modifier = Modifier
                                .size(56.dp),
                        model = ImageRequest.Builder(context = context)
                                .data(
                                        getDrawableResId(
                                                prefix = "topping_",
                                                imageName = topping.imageUrl
                                        )
                                )
                                .crossfade(true)
                                .build(),
                        contentDescription = topping.toppingName.plus("Image"),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.topping_cheese)
                )
*/
                Text(
                        text = topping.toppingName,
                        style = MaterialTheme.typography.Body3Regular.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                )

                if (selected) {

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                        Box(
                                modifier = Modifier
                                        .clip(shape = MaterialTheme.shapes.small)
                                        .border(
                                                width = MaterialTheme.spacing.spaceSingleDp,
                                                color = MaterialTheme.colorScheme.outline,
                                                shape = MaterialTheme.shapes.small
                                        ),
                                contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = stringResource(id = R.string.cds_text_back),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                                text = topping.counter.toString(),
                                style = MaterialTheme.typography.Title2.copy(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                )
                        )

                        Box(
                                modifier = Modifier
                                        .clip(shape = MaterialTheme.shapes.small)
                                        .border(
                                                width = MaterialTheme.spacing.spaceSingleDp,
                                                color = MaterialTheme.colorScheme.outline,
                                                shape = MaterialTheme.shapes.small
                                        ),
                                contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = stringResource(id = R.string.cds_text_back),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                                text = stringResource(
                                        id = R.string.dollar_price_tag,
                                        topping.toppingPrice
                                ),
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

@Composable
fun PizzaMetaData(
    pizzaName: String,
    ingredients: List<String>
) {

    Column(
            modifier = Modifier.fillMaxWidth(),
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

    val topping = Topping(
            toppingName = "Bacon",
            toppingPrice = 1.0,
            imageUrl = "",
            counter = 1
    )

    LazyPizzaTheme {

        Row (
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
                        .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            ToppingsCard(
                    modifier = Modifier.weight(1f),
                    topping = topping,
                    selected = false,
                    onEvent = {}
            )

            ToppingsCard(
                    modifier = Modifier.weight(1f),
                    topping = topping,
                    selected = true,
                    onEvent = {}
            )

            ToppingsCard(
                    modifier = Modifier.weight(1f),
                    topping = topping,
                    selected = false,
                    onEvent = {}
            )
        }
    }
}
