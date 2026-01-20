@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBarFour
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.PickupTimeOption
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.Label2Medium
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.TextSecondary
import com.tonyxlab.lazypizza.presentation.theme.Title1Medium
import com.tonyxlab.lazypizza.presentation.theme.Title3
import com.tonyxlab.lazypizza.utils.formatSelectedPickupTime
import com.tonyxlab.lazypizza.utils.ifThen
import com.tonyxlab.lazypizza.utils.toPickupTimeDisplayString

@Composable
fun OrderConfirmationContent(
    uiState: CheckoutUiState,
    onEvent: (CheckoutUiEvent) -> Unit,
    isDeviceWide: Boolean,
    modifier: Modifier = Modifier
) {

    val maxWidth = if (isDeviceWide)
        MaterialTheme.spacing.spaceTwoHundred * 2
    else
        Dp.Unspecified

    Column(
            modifier = modifier
                    .fillMaxSize()
                    .padding(all = MaterialTheme.spacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppTopBarFour(
                modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.spaceOneTwenty),
                onClick = { onEvent(CheckoutUiEvent.GoBack) }
        )

        Column(
                modifier = Modifier
                        .widthIn(max = maxWidth)
        ) {

            Column(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.spacing.spaceTen * 2),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                        text = stringResource(id = R.string.cap_text_order_placed),
                        style = MaterialTheme.typography.Title1Medium.copy(color = MaterialTheme.colorScheme.onSurface),
                        textAlign = TextAlign.Center
                )
                Text(
                        modifier = Modifier
                                .ifThen(isDeviceWide.not()) {

                                    padding(horizontal = MaterialTheme.spacing.spaceLarge)
                                },
                        text = stringResource(id = R.string.cap_text_order_confirmation),
                        style = MaterialTheme.typography.Body3Regular.copy(color = TextSecondary),
                        textAlign = TextAlign.Center
                )
            }

            Column(
                    modifier = Modifier
                            .border(
                                    width = MaterialTheme.spacing.spaceSingleDp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.spaceMedium),
                    verticalArrangement = Arrangement.spacedBy(
                            MaterialTheme.spacing.spaceDoubleDp * 3
                    )
            ) {
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                            text = stringResource(id = R.string.cap_text_order_number),
                            style = MaterialTheme.typography.Label2Medium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    )

                    Text(
                            text = uiState.orderNumber,
                            style = MaterialTheme.typography.Label2Medium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                            )
                    )
                }
                Row(

                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Text(
                            text = stringResource(id = R.string.cap_text_order_pickup_time),
                            style = MaterialTheme.typography.Label2SemiBold.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    )

                    Text(
                            text = formatPickupTime(uiState = uiState),
                            style = MaterialTheme.typography.Label2SemiBold.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                            )
                    )
                }
            }

            Box(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = MaterialTheme.spacing.spaceTen * 2),
                    contentAlignment = Alignment.Center
            ) {
                TextButton(
                        onClick = { onEvent(CheckoutUiEvent.ExitCheckout) }
                ) {
                    Text(
                            text = stringResource(id = R.string.txt_btn_back_to_menu),
                            style = MaterialTheme.typography.Title3.copy(
                                    color = MaterialTheme.colorScheme.primary
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun formatPickupTime(uiState: CheckoutUiState): String {
    return when (uiState.dateTimePickerState.pickupTimeOption) {

        PickupTimeOption.EARLIEST -> uiState.dateTimePickerState
                .earliestPickupTime
                .toPickupTimeDisplayString(isFullDate = true)

        PickupTimeOption.SCHEDULED -> formatSelectedPickupTime(
                uiState.dateTimePickerState.selectedDate,
                uiState.dateTimePickerState.selectedTime
        )

    }
}

@PreviewLightDark
@Composable
private fun OrderConfirmation_Content_Preview() {

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {

            OrderConfirmationContent(
                    uiState = CheckoutUiState(),
                    isDeviceWide = true,
                    onEvent = {}
            )
        }
    }
}