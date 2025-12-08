package com.tonyxlab.lazypizza.presentation.core.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.tonyxlab.lazypizza.presentation.theme.Background
import com.tonyxlab.lazypizza.presentation.theme.PrimaryGradientEnd
import com.tonyxlab.lazypizza.presentation.theme.PrimaryGradientStart
import com.tonyxlab.lazypizza.presentation.theme.SurfaceHigher
import com.tonyxlab.lazypizza.presentation.theme.SurfaceHighest

class GradientScheme(
    val stickyOverlayGradient: Brush = StickyOverlayGradient,
    val stickyButtonGradient: Brush = StickyButtonGradient
) {

    companion object {

        val StickyOverlayGradient =
            Brush.verticalGradient(

                  colors = listOf(SurfaceHigher,SurfaceHigher.copy(alpha = 0f))
            )

        val StickyButtonGradient = Brush.horizontalGradient(
                colors = listOf(PrimaryGradientEnd, PrimaryGradientStart)
        )
    }
}

val LocalGradient = staticCompositionLocalOf{ GradientScheme() }

val MaterialTheme.gradientScheme: GradientScheme
@Composable
get() = LocalGradient.current