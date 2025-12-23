package com.tonyxlab.lazypizza.presentation.screens.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.components.AppButton
import com.tonyxlab.lazypizza.presentation.core.components.AppInputField
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiEvent
import com.tonyxlab.lazypizza.presentation.screens.auth.handling.AuthUiState
import com.tonyxlab.lazypizza.presentation.theme.Body2Regular
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.RoundedCornerShape100
import com.tonyxlab.lazypizza.presentation.theme.Title1Medium
import com.tonyxlab.lazypizza.presentation.theme.Title3

@Composable
fun PhoneInputSection(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    onEvent: (AuthUiEvent) -> Unit,
) {

    Column(
            modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.spacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceDoubleDp * 3),
                text = stringResource(id = R.string.cap_text_welcome),
                style = MaterialTheme.typography.Title1Medium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                )
        )

        Text(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spaceTen * 2),
                text = stringResource(id = R.string.cap_text_enter_phone_number),
                style = MaterialTheme.typography.Body3Regular.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        )

        AppInputField(
                modifier = Modifier
                        .height(MaterialTheme.spacing.spaceTwelve * 4),
                textFieldState = uiState.phoneInputState.textFieldState,
                textStyle = MaterialTheme.typography.Body2Regular.copy(
                        color = MaterialTheme.colorScheme.onSurface
                ),
                placeholderText = "+1 000 000 0000",
                placeholderTextStyle = MaterialTheme.typography.Body2Regular.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.RoundedCornerShape100,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))

        AppButton(
                modifier = Modifier
                        .height(MaterialTheme.spacing.spaceTen * 4)
                        .fillMaxWidth(),
                onClick = { onEvent(AuthUiEvent.ContinueToLoginIn) },
                buttonText = stringResource(id = R.string.btn_text_continue),
                enabled = uiState.phoneInputState.continueEnabled
        )

        TextButton(
                onClick = { onEvent(AuthUiEvent.ContinueWithoutLogin) }) {
            Text(
                    text = stringResource(id = R.string.txt_btn_continue_without_sign_in),
                    style = MaterialTheme.typography.Title3
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PhoneInputSection_Preview() {
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {

            PhoneInputSection(uiState = AuthUiState()) { }
        }
    }
}