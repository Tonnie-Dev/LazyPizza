package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.gradientScheme
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Primary8
import com.tonyxlab.lazypizza.presentation.theme.RoundedCornerShape100
import com.tonyxlab.lazypizza.presentation.theme.Title3
import com.tonyxlab.lazypizza.utils.ifThen

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String,
    isOutlineButton: Boolean = false,
    buttonShape: Shape = MaterialTheme.shapes.RoundedCornerShape100,
    buttonHeight: Dp = MaterialTheme.spacing.spaceTwelve * 4,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Box(
            modifier = modifier
                    .clip(shape = buttonShape)
                    .ifThen(flag = isOutlineButton) {
                        background(
                                color = Color.Transparent,
                                shape = buttonShape
                        )
                                .border(
                                        width = MaterialTheme.spacing.spaceDoubleDp,
                                        color = Primary8,
                                        shape = buttonShape
                                )
                    }
                    .ifThen(flag = isOutlineButton.not()) {
                        background(
                                brush = MaterialTheme.gradientScheme.stickyButtonGradient,
                                shape = buttonShape
                        )
                    }
                    .height(buttonHeight)
                    .clickable { onClick() }

                    .padding(
                            horizontal = MaterialTheme.spacing.spaceTwelve * 2,
                            vertical = MaterialTheme.spacing.spaceTen
                    ),
            contentAlignment = Alignment.Center) {

        Text(
                modifier = Modifier.animateContentSize(
                        animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessHigh
                        )
                ),
                text = buttonText,
                style = MaterialTheme.typography.Title3.copy(
                        color = contentColor
                )
        )
    }
}

@PreviewLightDark
@Composable
private fun AppButton_Preview() {
    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppButton(
                    buttonText = stringResource(
                            id = R.string.btn_text_add_to_cart_with_price, 13.13
                    ),
                    onClick = {}
            )

            AppButton(
                    buttonText = stringResource(id = R.string.btn_text_add_to_cart),
                    isOutlineButton = true,
                    contentColor = MaterialTheme.colorScheme.primary,
                    onClick = {}
            )
        }
    }
}
