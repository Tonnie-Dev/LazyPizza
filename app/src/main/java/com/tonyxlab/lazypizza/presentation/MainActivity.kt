package com.tonyxlab.lazypizza.presentation

import android.graphics.Color.toArgb
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tonyxlab.lazypizza.navigation.NavRoot
import com.tonyxlab.lazypizza.presentation.theme.Background
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
        }
        enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.light(
                        scrim =Background.toArgb(),
                        darkScrim = Background.toArgb()
                )
        )
        setContent {
            LazyPizzaTheme {
                Box(
                        contentAlignment = Alignment.Center
                ) {
                    NavRoot()
                }

            }
        }
    }
}
