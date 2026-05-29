package com.tonyxlab.lazypizza.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.utils.ifThen
import timber.log.Timber

@Composable
fun DisplayImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    imageSize: Dp = MaterialTheme.spacing.spaceDefault,
    containerSize: Dp = MaterialTheme.spacing.spaceDefault,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentDescription: String = stringResource(id = R.string.cds_text_image),
    padding: PaddingValues = PaddingValues(0.dp),
    @DrawableRes
    fallbackDrawableRes: Int = R.drawable.pizza_margherita,
    @DrawableRes
    errorDrawableRes: Int = R.drawable.pizza_margherita
) {

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    Box(
            modifier = modifier
                    .clip(shape = shape)
                    .background(color = backgroundColor)
                    .ifThen(containerSize > 0.dp) {
                        size(size = containerSize)
                    }
                    .ifThen(containerSize == 0.dp) {
                        fillMaxWidth()
                    }
                    .padding(padding),
            contentAlignment = Alignment.Center
    ) {

        if (isLoading) {
            Box(
                    modifier = Modifier
                            .matchParentSize()
                            .shimmerEffect()
            )
        }

        AsyncImage(
                modifier = Modifier
                        .ifThen(imageSize > 0.dp) {
                            size(size = imageSize)
                        }
                        .ifThen(imageSize <= 0.dp) {
                            fillMaxWidth()
                        }
                        .aspectRatio(1f),
                model = ImageRequest.Builder(context = context)
                        .data(imageUrl)
                        .crossfade(true)
                        .fallback(fallbackDrawableRes)
                        .error(errorDrawableRes)
                        .build(),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                onLoading = { isLoading = true },
                onSuccess = { isLoading = false },
                onError = { error ->
                    isLoading = false
                    Timber.tag("CoilError")
                            .e(error.result.throwable)

                }
        )
    }
}

@Composable
fun Modifier.shimmerEffect(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmerTransition")

    val translateAnim by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                    animation = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
            ),
            label = "shimmerTranslate"
    )

    val shimmerColors = listOf(
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
    )

    background(
            brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset.Zero,
                    end = Offset(x = translateAnim, y = translateAnim)
            )
    )
}



