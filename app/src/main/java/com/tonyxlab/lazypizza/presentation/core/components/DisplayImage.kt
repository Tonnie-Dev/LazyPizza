package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tonyxlab.lazypizza.R

@Composable
fun DisplayImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    
    AsyncImage(
            modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            model = ImageRequest.Builder(context = context)
                    .data(getDrawableResId(imageUrl))
                    .crossfade(true)
                    .build(),
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.pizza_pepperoni)
    )
}

@Composable
fun getDrawableResId(imageName: String): Int {
    val context = LocalContext.current
    val cleanName = imageName.substringBeforeLast('.') // remove .png or .jpg
    return context.resources.getIdentifier(
            "pizza_$cleanName", "drawable", context.packageName
    )
}

