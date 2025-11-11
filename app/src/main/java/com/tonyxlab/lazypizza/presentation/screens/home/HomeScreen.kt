package com.tonyxlab.lazypizza.presentation.screens.home

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBar
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    BaseContentLayout(
            modifier = modifier.padding(MaterialTheme.spacing.spaceMedium),
            viewModel = viewModel,
            topBar = {
                AppTopBar(
                        modifier = Modifier.padding(MaterialTheme.spacing.spaceMedium),
                        phoneNumber = uiState.phoneNumber, onCallClick = {
                    viewModel.onEvent(HomeUiEvent.PlaceCall)
                }
                )
            },
            actionEventHandler = { _, action ->

                when (action) {

                    HomeActionEvent.NavigateToWhereSunNeverShines -> {
                        val dialIntent = Intent(Intent.ACTION_DIAL).apply {

                            data = "tel:${uiState.phoneNumber}".toUri()

                        }
                        context.startActivity(dialIntent)
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

        }
    }
}