@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.history.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.domain.model.Order
import com.tonyxlab.lazypizza.domain.model.OrderStatus
import com.tonyxlab.lazypizza.domain.model.itemsSummary
import com.tonyxlab.lazypizza.presentation.core.components.LazyListComponent
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body4Regular
import com.tonyxlab.lazypizza.presentation.theme.Label3SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Success
import com.tonyxlab.lazypizza.presentation.theme.TextPrimaryAlternate
import com.tonyxlab.lazypizza.presentation.theme.Title3
import com.tonyxlab.lazypizza.presentation.theme.Warning
import com.tonyxlab.lazypizza.utils.OrderMock
import com.tonyxlab.lazypizza.utils.toFormattedDate

@Composable
fun OrderItemCard(
    order: Order,
    modifier: Modifier = Modifier
) {

    val (color, status) = when (order.orderStatus) {

        OrderStatus.COMPLETED -> Success to
                stringResource(id = R.string.cap_text_order_completed)

        OrderStatus.IN_PROGRESS -> Warning to
                stringResource(id = R.string.cap_text_order_in_progress)

        OrderStatus.CANCELLED -> Warning to
                stringResource(id = R.string.cap_text_order_cancelled)
    }

    Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {

        Column(
                modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                        .padding(vertical = MaterialTheme.spacing.spaceTwelve),
                verticalArrangement = Arrangement.spacedBy(
                        space = MaterialTheme.spacing.spaceMedium
                )
        ) {

            // Row 1
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                        verticalArrangement = Arrangement.spacedBy(
                                MaterialTheme.spacing.spaceDoubleDp
                        )
                ) {

                    Text(
                            text = order.orderNumber,
                            style = MaterialTheme.typography.Title3.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                            )
                    )

                    Text(
                            text = order.placedAt.toFormattedDate(),
                            style = MaterialTheme.typography.Body4Regular.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    )
                }

                Box(
                        modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(color = color)
                                .padding(horizontal = MaterialTheme.spacing.spaceSmall)
                                .padding(vertical = MaterialTheme.spacing.spaceDoubleDp)
                ) {

                    Text(
                            text = status, style = MaterialTheme.typography.Label3SemiBold.copy(
                            color = MaterialTheme.colorScheme.surface
                    )
                    )

                }
            }

            // Row 2
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                        text = order.itemsSummary(),
                        style = MaterialTheme.typography.Body4Regular.copy(
                                color = TextPrimaryAlternate
                        )
                )

                Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(
                                space = MaterialTheme.spacing.spaceExtraSmall
                        )
                ) {

                    Text(
                            text = stringResource(id = R.string.cap_text_total_amount),
                            style = MaterialTheme.typography.Body4Regular.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    )

                    Text(
                            text = stringResource(
                                    id = R.string.cap_text_total_amount_formatted,
                                    order.totalAmount
                            ),
                            style = MaterialTheme.typography.Title3.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.End
                            )
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OrderItemCard_Preview() {
    val orders = OrderMock.generateOrders()

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(all = MaterialTheme.spacing.spaceMedium),

                ) {

            LazyListComponent(
                    items = orders,
                    key = { it.id },
                    //isDeviceWide = true
            ) {

                OrderItemCard(order = it)
            }

        }
    }
}
