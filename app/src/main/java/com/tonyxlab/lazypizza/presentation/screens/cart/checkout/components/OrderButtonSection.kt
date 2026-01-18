package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.components.AppButton
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.cart.checkout.handling.CheckoutUiEvent
import com.tonyxlab.lazypizza.presentation.theme.Label1Medium
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title3

@Composable
fun OrderButtonSection(
    totalOrderAmount: Double,
    onEvent: (CheckoutUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    isWideDevice: Boolean = false,
) {
    Box(
            modifier = modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(all = MaterialTheme.spacing.spaceMedium),
            contentAlignment = Alignment.Center
    ) {

        if (isWideDevice) {
            WideButtonContent(
                    modifier = Modifier,
                    totalOrderAmount = totalOrderAmount,
                    onClick = {
                        onEvent(it)
                    }
            )
        } else {

            NarrowButtonContent(
                    modifier = Modifier,
                    totalOrderAmount = totalOrderAmount,
                    onClick = {
                        onEvent(it)
                    }
            )
        }
    }
}

@Composable
private fun NarrowButtonContent(
    totalOrderAmount: Double,
    onClick: (CheckoutUiEvent) -> Unit,
    modifier: Modifier = Modifier

) {

    Column(modifier = modifier) {
        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.spacing.spaceTwelve)
                        .semantics(mergeDescendants = true) {},
                horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                    text = stringResource(id = R.string.cap_text_order_total),
                    style = MaterialTheme.typography.Label1Medium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )
            Text(
                    text = stringResource(
                            id = R.string.cap_text_order_total_amount,
                            totalOrderAmount
                    ), style = MaterialTheme.typography.Title3.copy(
                    color = MaterialTheme.colorScheme.onSurface
            )
            )
        }
        AppButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(id = R.string.btn_text_place_order),
                onClick = { onClick(CheckoutUiEvent.PlaceOrder) })
    }
}

@Composable
private fun WideButtonContent(
    totalOrderAmount: Double,
    onClick: (CheckoutUiEvent) -> Unit,
    modifier: Modifier = Modifier

) {
    Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
                modifier = modifier
                        .weight(.4f)
                        .semantics(mergeDescendants = true) {},
                horizontalArrangement = Arrangement.spacedBy(
                        space = MaterialTheme.spacing.spaceExtraSmall
                )
        ) {

            Text(
                    text = stringResource(id = R.string.cap_text_order_total),
                    style = MaterialTheme.typography.Label1Medium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )
            Text(
                    text = stringResource(
                            id = R.string.cap_text_order_total_amount,
                            totalOrderAmount
                    ),
                    style = MaterialTheme.typography.Title3.copy(
                            color = MaterialTheme.colorScheme.onSurface
                    )
            )
        }

        AppButton(
                modifier = Modifier
                        .weight(.6f)
                        .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                        .padding(top = MaterialTheme.spacing.spaceSmall),
                buttonText = stringResource(id = R.string.btn_text_place_order),
                onClick = { onClick(CheckoutUiEvent.PlaceOrder) })
    }
}

@Preview
@Composable
private fun OrderButtonSection_Preview() {
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            NarrowButtonContent(
                    modifier = Modifier
                            .border(
                                    color = MaterialTheme.colorScheme.outline,
                                    width = MaterialTheme.spacing.spaceDoubleDp
                            )
                            .padding(bottom = MaterialTheme.spacing.spaceMedium),
                    totalOrderAmount = 20.96,
                    onClick = {}
            )

            WideButtonContent(
                    modifier = Modifier
                            .border(
                                    color = MaterialTheme.colorScheme.outline,
                                    width = MaterialTheme.spacing.spaceDoubleDp
                            )
                            .padding(bottom = MaterialTheme.spacing.spaceMedium),
                    totalOrderAmount = 13.29,
                    onClick = {}
            )
        }
    }
}


