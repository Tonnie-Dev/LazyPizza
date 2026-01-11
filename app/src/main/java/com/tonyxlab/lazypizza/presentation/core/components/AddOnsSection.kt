package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.AddOnItem
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body1Regular
import com.tonyxlab.lazypizza.presentation.theme.HorizontalRoundedCornerShape12
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold
import com.tonyxlab.lazypizza.utils.getMockSideItems

@Composable
fun AddOnsSection(
    items: List<AddOnItem>,
    onAddItem: (AddOnItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.spaceTen * 2)
    ) {

            Text(
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceSmall),
                    text = stringResource(R.string.header_text_recommended_options),
                    style = MaterialTheme.typography.Label2SemiBold.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )

        LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall)
        ) {

            items(items = items, key = { it.id }) { item ->
                AddOnItem(
                        addOnItem = item,
                        onAddItem = { onAddItem(item) }
                )
            }
        }
    }
}

@Composable
private fun AddOnItem(
    addOnItem: AddOnItem,
    onAddItem: (AddOnItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier
                    .border(
                            width = MaterialTheme.spacing.spaceSingleDp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                    ),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.width(MaterialTheme.spacing.spaceMedium * 10)) {

            DisplayImage(
                    imageUrl = addOnItem.imageUrl,
                    imageSize = MaterialTheme.spacing.spaceTwelve * 9,
                    shape = MaterialTheme.shapes.HorizontalRoundedCornerShape12
            )

            Column(
                    modifier = Modifier.padding(all = MaterialTheme.spacing.spaceTwelve),
                    verticalArrangement = Arrangement.spacedBy(
                            MaterialTheme.spacing.spaceSmall
                    )
            ) {

                Text(
                        text = addOnItem.name,
                        style = MaterialTheme.typography.Body1Regular.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                )
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                            text = stringResource(
                                    id = R.string.dollar_price_tag,
                                    addOnItem.price
                            ),
                            style = MaterialTheme.typography.Title1SemiBold.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                            )
                    )

                    Box(
                            modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .border(
                                            width = MaterialTheme.spacing.spaceSingleDp,
                                            color = MaterialTheme.colorScheme.outline,
                                            shape = MaterialTheme.shapes.small
                                    )
                                    .clickable {
                                        onAddItem(addOnItem)
                                    }
                                    .padding(MaterialTheme.spacing.spaceExtraSmall),
                            contentAlignment = Alignment.Center
                    ) {
                        Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.cds_text_add),
                                tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AddOnsRow_PreviewSection() {

    val sideItems = getMockSideItems()

    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddOnsSection(
                    items = sideItems,
                    onAddItem = {}
            )
        }
    }
}