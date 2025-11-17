package com.tonyxlab.lazypizza.presentation.core.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import com.tonyxlab.lazypizza.presentation.theme.Background
import com.tonyxlab.lazypizza.presentation.theme.PrimaryGradientEnd
import com.tonyxlab.lazypizza.presentation.theme.PrimaryGradientStart
import com.tonyxlab.lazypizza.presentation.theme.SurfaceHigher

class GradientScheme(
    val stickyOverlayGradient: Brush = StickyOverlayGradient,
    val stickyButtonGradient: Brush = StickyButtonGradient
) {

    companion object {

        val StickyOverlayGradient =
            Brush.verticalGradient(
                    colors = listOf(Background, SurfaceHigher)
            )

        val StickyButtonGradient = Brush.horizontalGradient(
                colors = listOf(PrimaryGradientStart, PrimaryGradientEnd)
        )
    }
}

val LocalGradient = staticCompositionLocalOf{ GradientScheme() }

val MaterialTheme.gradientScheme: GradientScheme
@Composable
get() = LocalGradient.current