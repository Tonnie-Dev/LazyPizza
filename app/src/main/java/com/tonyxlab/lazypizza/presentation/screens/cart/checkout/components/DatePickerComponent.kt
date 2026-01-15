@file:OptIn(ExperimentalMaterial3Api::class)

package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.components.AppButton
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title3
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DatePickerComponent(
    uiState: CheckoutUiState,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {

    val today = LocalDate.now()

    val datePickerState = rememberDatePickerState(
            selectableDates = object : SelectableDates {

                override fun isSelectableDate(utcTimeMillis: Long): Boolean {

                    val selectedDate =
                        Instant.ofEpochMilli(utcTimeMillis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                    return !selectedDate.isBefore(today)
                }
            }
    )

    if (uiState.dateTimePickerState.showDatePicker) {
        DatePickerDialog(
                onDismissRequest = onDismiss,
                confirmButton = {
                    AppButton(
                            modifier = Modifier
                                    .padding(end = MaterialTheme.spacing.spaceMedium)
                                    .padding(bottom = MaterialTheme.spacing.spaceTwelve),
                            onClick = {

                                datePickerState.selectedDateMillis?.let {

                                    val date = Instant.ofEpochMilli(it)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate()
                                    onDateSelected(date)
                                }
                            },
                            buttonText = stringResource(id = R.string.btn_text_ok)
                    )
                },
                dismissButton = {
                    TextButton(onClick = { onDismiss() }) {
                        Text(
                                text = stringResource(R.string.txt_btn_cancel),
                                style = MaterialTheme.typography.Title3.copy(
                                        color = MaterialTheme.colorScheme.primary
                                )
                        )
                    }
                }, colors = DatePickerDefaults.colors()
        ) {

            DatePicker(state = datePickerState)
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
            DatePickerComponent(
                    uiState = CheckoutUiState(),
                    onDateSelected = {},
                    onDismiss = {}
            )
        }
    }
}
