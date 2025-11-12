package com.tonyxlab.lazypizza.presentation.screens.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.base.BaseContentLayout
import com.tonyxlab.lazypizza.presentation.core.components.AppTopBar
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.home.components.SearchComponent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeActionEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiEvent
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.SetStatusBarIconsColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    SetStatusBarIconsColor(darkIcons = true)
    BaseContentLayout(
            modifier = modifier,
            viewModel = viewModel,
            topBar = {
                AppTopBar(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spaceMedium),
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
    ) { uiState ->
        HomeScreenContent(
                uiState = uiState,
                onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.spacing.spaceMedium)
    ) {

        Image(
                modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .fillMaxWidth(),
                painter = painterResource(R.drawable.banner_small),
                contentDescription = stringResource(R.string.cds_text_banner),
                contentScale = ContentScale.Crop
        )

        SearchComponent(
                modifier = Modifier.padding(top = 0.dp),
                uiState = uiState
        )
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenContent_Preview() {

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
        ) {
            HomeScreenContent(
                    uiState = HomeUiState(),
                    onEvent = {}
            )
        }
    }
}