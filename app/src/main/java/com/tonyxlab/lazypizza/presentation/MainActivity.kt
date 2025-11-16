package com.tonyxlab.lazypizza.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tonyxlab.lazypizza.navigation.NavRoot
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
        }

        enableEdgeToEdge()

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
