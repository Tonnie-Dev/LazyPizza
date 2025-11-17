package com.tonyxlab.lazypizza.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun DisplayImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    size: Dp = MaterialTheme.spacing.spaceDefault,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentDescription: String = stringResource(id = R.string.cds_text_image),
    padding: PaddingValues = PaddingValues(0.dp),
    prefix: String = "pizza_",
    @DrawableRes
    fallbackDrawableRes: Int = R.drawable.pizza_margherita,
    @DrawableRes
    errorDrawableRes: Int = R.drawable.pizza_margherita
) {

    val context = LocalContext.current

    Box(
            modifier = modifier
                    .clip(shape = shape)
                    .background(color = backgroundColor)
                    .ifThen(size > 0.dp) {
                        size(size = size)
                    }
                    .ifThen(size <= 0.dp) {
                        fillMaxWidth()
                    }
                    .padding(padding),
            contentAlignment = Alignment.Center
    ) {

        AsyncImage(
                modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                model = ImageRequest.Builder(context = context)
                        .data(getDrawableResId(imageName = imageUrl, prefix =  prefix))
                        .crossfade(true)
                        .fallback(fallbackDrawableRes)
                        .error(errorDrawableRes)
                        .build(),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.pizza_pepperoni)
        )
    }
}

@Composable
fun getDrawableResId(
    prefix: String,
    imageName: String
): Int? {
    val context = LocalContext.current
    val cleanName = imageName.substringBeforeLast('.') // remove .png or .jpg
    val id = context.resources.getIdentifier(
            "$prefix$cleanName", "drawable", context.packageName
    )
    return id.takeIf { it != 0 }
}

