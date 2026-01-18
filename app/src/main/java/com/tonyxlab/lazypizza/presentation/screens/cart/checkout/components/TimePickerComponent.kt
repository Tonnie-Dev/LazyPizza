@file:OptIn(ExperimentalMaterial3Api::class)
@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components

import android.R.attr.maxWidth
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.components.AppButton
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title3
import java.time.LocalTime


@Composable
fun TimePickerComponent(
    uiState: CheckoutUiState,
    onTimeSelected: (LocalTime) -> Unit,
    onEvent: (CheckoutUiEvent) -> Unit,
    isDeviceWide: Boolean,
    modifier: Modifier = Modifier
) {

    val timePickerState = rememberTimePickerState(is24Hour = false)

    val showTimePicker = uiState.dateTimePickerState.showTimePicker

    val error = uiState.dateTimePickerState.pickTimeError

    LaunchedEffect(showTimePicker) {

        if (showTimePicker) {
            onEvent(
                    CheckoutUiEvent.PreviewTime(
                            time = LocalTime.of(
                                    timePickerState.hour,
                                    timePickerState.minute
                            )
                    )
            )
        }
    }

    LaunchedEffect(showTimePicker) {

        snapshotFlow { timePickerState.hour to timePickerState.minute }
                .collect { (hour, minute) ->
                    onEvent(CheckoutUiEvent.PreviewTime(LocalTime.of(hour, minute)))
                }
    }

    if (showTimePicker) {

        AlertDialog(
                modifier = modifier,
                onDismissRequest = { onEvent(CheckoutUiEvent.DismissPicker) },
                confirmButton = {

                    AppButton(
                            modifier = Modifier
                                    .padding(end = MaterialTheme.spacing.spaceMedium)
                                    .padding(bottom = MaterialTheme.spacing.spaceTwelve),
                            enabled = error == null,
                            onClick = {
                                onTimeSelected(
                                        LocalTime.of(
                                                timePickerState.hour,
                                                timePickerState.minute
                                        )
                                )
                                onEvent(CheckoutUiEvent.DismissPicker)
                            },
                            buttonText = stringResource(id = R.string.btn_text_ok)
                    )
                },
                dismissButton = {
                    TextButton(
                            onClick = { onEvent(CheckoutUiEvent.DismissPicker) }
                    ) {
                        Text(
                                text = stringResource(id = R.string.txt_btn_cancel),
                                style = MaterialTheme.typography.Title3.copy(
                                        color = MaterialTheme.colorScheme.primary
                                )
                        )
                    }
                },
                text = {

                    Column {
                        TimePicker(state = timePickerState)

                       /* if (error != null) {
                            Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.spaceSmall))
                            Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(id = error),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.Label2SemiBold.copy(
                                            color = MaterialTheme.colorScheme.primary
                                    )
                            )
                        }*/


                        AnimatedVisibility(
                                visible = error != null,
                                enter = fadeIn() + slideInVertically(
                                        initialOffsetY = { it / 4 }
                                ),
                                exit = fadeOut() + slideOutVertically(
                                        targetOffsetY = { it / 4 }
                                )
                        ) {
                            error?.let { errorRes ->

                                Column {
                                    Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.spaceSmall))

                                    Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = stringResource(id = errorRes),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.Label2SemiBold.copy(
                                                    color = MaterialTheme.colorScheme.primary
                                            )
                                    )
                                }
                            }

                        }

                            }
                }
        )
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
            TimePickerComponent(
                    uiState = CheckoutUiState(),
                    onTimeSelected = {},
                    onEvent = {},
                    isDeviceWide = false
            )
        }
    }
}
