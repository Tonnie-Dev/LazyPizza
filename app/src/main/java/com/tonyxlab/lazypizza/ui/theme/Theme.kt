package com.tonyxlab.lazypizza.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = lightColorScheme(

        primary = Primary,
        onPrimary = TextOnPrimary,
        primaryContainer = PrimaryGradientStart,
        onPrimaryContainer = TextPrimary,

        secondary = TextSecondary,
        onSecondary = TextOnPrimary,
        secondaryContainer = SurfaceHigher,
        onSecondaryContainer = TextPrimary,

        background = Background,
        onBackground = TextPrimary,

        surface = SurfaceHigher,
        onSurface = TextPrimary,
        surfaceVariant = SurfaceHighest,
        onSurfaceVariant = TextSecondary,

        error = Color(0xFFB3261E),
        onError = Color.White,
        errorContainer = Color(0xFFF9DEDC),
        onErrorContainer = Color(0xFF410E0B),

        outline = Outline,
        outlineVariant = Outline50,

        scrim = Color(0x33000000),
        surfaceTint = Primary,
        inverseSurface = SurfaceHighest,
        inverseOnSurface = TextPrimary,
        inversePrimary = Primary

)

@Composable
fun LazyPizzaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
            colorScheme = AppColorScheme,
            typography = Typography,
            content = content,
            shapes = customMaterialShapes
    )
}