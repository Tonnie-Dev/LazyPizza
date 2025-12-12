package com.tonyxlab.lazypizza.presentation.screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tonyxlab.lazypizza.presentation.core.components.AppButton
import com.tonyxlab.lazypizza.presentation.core.utils.gradientScheme
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.details.handling.DetailsUiEvent

@Composable
fun StickyAddToCart(
    buttonText: String,
    onEvent: (DetailsUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
            modifier = modifier
                    .background(brush = MaterialTheme.gradientScheme.stickyOverlayGradient)
                    .height(height = MaterialTheme.spacing.spaceOneHundred)
                    .fillMaxWidth(),
            contentAlignment = Alignment.Center
    ) {

        AppButton(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                        .padding(top = MaterialTheme.spacing.spaceSmall),
                buttonText = buttonText,
                onClick = { onEvent(DetailsUiEvent.AddToCart) }
        )
    }
}

