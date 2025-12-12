package com.tonyxlab.lazypizza.presentation.core.components

import android.R.attr.top
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1SemiBold

@Composable
fun EmptyScreenContent(
    title: String,
    subTitle: String,
    buttonText: String,
    onEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
            modifier = modifier
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceTen * 2),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceDoubleDp * 3)
        ) {

            Text(
                    text = title,
                    style = MaterialTheme.typography.Title1SemiBold.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                    )
            )

            Text(
                    text = subTitle,
                    style = MaterialTheme.typography.Body3Regular.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                    )
            )
        }

        AppButton(
                onClick = onEvent,
                buttonText = buttonText
        )
    }
}

@PreviewLightDark
@Composable
private fun EmptyScreenContent_Preview() {
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
        ) {

            EmptyScreenContent(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.spaceTwelve * 10),
                    title = stringResource(id = R.string.cap_text_empty_cart),
                    subTitle = stringResource(id = R.string.cap_text_head_back),
                    buttonText = stringResource(id = R.string.btn_text_back_to_menu),
                    onEvent = {}
            )
        }
    }
}
