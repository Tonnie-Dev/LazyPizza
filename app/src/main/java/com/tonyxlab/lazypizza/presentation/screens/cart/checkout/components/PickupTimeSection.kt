@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.PickupTimeOption
import com.tonyxlab.lazypizza.presentation.theme.Body3Medium
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.calculateEarliestPickupTime
import java.time.LocalDateTime

@Composable
fun PickupTimeSection(
    uiState: CheckoutUiState,
    onEvent: (CheckoutUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    isDeviceWide: Boolean = false,
    headerTextStyle: TextStyle = MaterialTheme.typography.Label2SemiBold
) {
    Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceTwelve)
    ) {
        Text(
                modifier = Modifier,
                text = stringResource(id = R.string.header_text_pickup_time),
                style = headerTextStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (isDeviceWide) {

            Row(
                    horizontalArrangement = Arrangement.spacedBy(
                            space = MaterialTheme.spacing.spaceSmall
                    )
            ) {
                TimeSlotItem(
                        modifier = modifier.weight(1f),
                        text = stringResource(id = R.string.btn_text_earliest_time),
                        active = uiState.pickupTimeOption == PickupTimeOption.EARLIEST,
                ) {
                    onEvent(
                            CheckoutUiEvent.SelectPickupTime(
                                    pickupTimeOption = PickupTimeOption.EARLIEST
                            )
                    )
                }


                TimeSlotItem(
                        modifier = modifier.weight(1f),
                        text = stringResource(id = R.string.btn_text_schedule),
                        active = uiState.pickupTimeOption == PickupTimeOption.SCHEDULED,
                ) {
                    onEvent(
                            CheckoutUiEvent.SelectPickupTime(
                                    pickupTimeOption = PickupTimeOption.SCHEDULED
                            )
                    )
                }

            }
        } else {
            Column(
                    modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.spaceTwelve),
                    verticalArrangement = Arrangement.spacedBy(
                            space = MaterialTheme.spacing.spaceSmall
                    )
            )
            {
                TimeSlotItem(
                        text = stringResource(id = R.string.btn_text_earliest_time),
                        active = uiState.pickupTimeOption == PickupTimeOption.EARLIEST,
                ) {
                    onEvent(
                            CheckoutUiEvent.SelectPickupTime(
                                    pickupTimeOption = PickupTimeOption.EARLIEST
                            )
                    )
                }
                TimeSlotItem(
                        text = stringResource(id = R.string.btn_text_schedule),
                        active = uiState.pickupTimeOption == PickupTimeOption.SCHEDULED,
                )
                {
                    onEvent(
                            CheckoutUiEvent.SelectPickupTime(
                                    pickupTimeOption = PickupTimeOption.SCHEDULED
                            )
                    )
                }
            }
        }

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                    text = stringResource(id = R.string.header_text_earliest_pickup_time),
                    style = headerTextStyle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                    text = LocalDateTime.now()
                            .calculateEarliestPickupTime(),
                    style = headerTextStyle,
                    color = MaterialTheme.colorScheme.onSurface
            )
        }

        HorizontalDivider(
                modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.spaceMedium)
        )
    }
}

@Composable
private fun TimeSlotItem(
    text: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
            modifier = modifier
                    .clip(RoundedCornerShape(100))
                    .border(
                            width = MaterialTheme.spacing.spaceSingleDp,
                            color = if (active)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(100)
                    )
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.spaceTwelve * 4)
                    .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                    ) { onClick() }
                    .padding(start = MaterialTheme.spacing.spaceTen * 2)
                    .padding(end = MaterialTheme.spacing.spaceTen)
    ) {

        Row(
                modifier = Modifier
                        .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                    selected = active,
                    onClick = null
            )

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceSmall))

            Text(
                    text = text,
                    style = MaterialTheme.typography.Body3Medium.copy(
                            color = if (active)
                                MaterialTheme.colorScheme.onSurface
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PickupTimeSection_Preview() {
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {
            PickupTimeSection(
                    modifier = Modifier.statusBarsPadding(),
                    uiState = CheckoutUiState(),
                    onEvent = {}
            )
        }
    }
}