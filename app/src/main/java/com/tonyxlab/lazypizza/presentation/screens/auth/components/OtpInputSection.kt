package com.tonyxlab.lazypizza.presentation.screens.auth.components

import android.app.Activity
import android.content.IntentFilter
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.platform.SmsConsentReceiver
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
import com.tonyxlab.lazypizza.presentation.theme.Title4
import com.tonyxlab.lazypizza.utils.ifThen
import timber.log.Timber

@Composable
fun OtpInputSection(
    uiState: AuthUiState,
    modifier: Modifier = Modifier,
    onEvent: (AuthUiEvent) -> Unit,
    activity: Activity
) {
    val context = LocalContext.current

    val consentLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            // User denied or cancelled → do nothing
            return@rememberLauncherForActivityResult
        }

        val message =
            result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)

        Timber.tag("SmsConsentReceiver")
                .i("Result Code is ${result.resultCode}")
        val otp = extractOtp(message)

        if (otp != null) {
            onEvent(AuthUiEvent.FillOtp(otp))
        }
    }

    DisposableEffect(Unit) {
        val receiver = SmsConsentReceiver { consentIntent ->
            consentLauncher.launch(consentIntent)
        }

        val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

        ContextCompat.registerReceiver(
                context,
                receiver,
                filter,
                ContextCompat.RECEIVER_NOT_EXPORTED
        )

        // 3) Start listening for ONE incoming message (from any sender)
        SmsRetriever.getClient(context)
                .startSmsUserConsent(null)

        onDispose {
            runCatching { context.unregisterReceiver(receiver) }
        }
    }

    LaunchedEffect(uiState.otpRequestId) {

        if (uiState.otpRequestId == 0) return@LaunchedEffect
        // delay(5000)
        SmsRetriever.getClient(context)
                .startSmsUserConsent(null)
    }


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
                text = stringResource(id = R.string.cap_text_enter_code),
                style = MaterialTheme.typography.Body3Regular.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        )

        AppInputField(
                modifier = Modifier
                        .height(MaterialTheme.spacing.spaceTwelve * 4)
                        .padding(bottom = MaterialTheme.spacing.spaceTwelve),
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

        OtpInput(
                modifier = Modifier
                        .ifThen(uiState.otpInputState.error) {
                            padding(
                                    bottom = MaterialTheme.spacing.spaceSmall
                            )

                        }
                        .ifThen(!uiState.otpInputState.error) {

                            padding(
                                    bottom = MaterialTheme.spacing.spaceMedium
                            )
                        },

                textFieldState = uiState.otpInputState.textFieldState,
                error = uiState.otpInputState.error
        )

        AnimatedVisibility(
                visible = uiState.otpInputState.error,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                exit = fadeOut()
        ) {

            Text(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MaterialTheme.spacing.spaceMedium)
                            .padding(bottom = MaterialTheme.spacing.spaceMedium),
                    text = stringResource(id = R.string.cap_text_incorrect_code),
                    style = MaterialTheme.typography.Title4.copy(
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Start
                    )
            )
        }

        AppButton(
                modifier = Modifier
                        .height(MaterialTheme.spacing.spaceTen * 4)
                        .fillMaxWidth(),
                onClick = { onEvent(AuthUiEvent.ConfirmOtp) },
                buttonText = stringResource(id = R.string.btn_text_confirm),
                enabled = uiState.otpInputState.confirmEnabled
        )

        TextButton(
                onClick = { onEvent(AuthUiEvent.ContinueWithoutLogin) }) {

            Text(
                    text = stringResource(id = R.string.txt_btn_continue_without_sign_in),
                    style = MaterialTheme.typography.Title3
            )
        }

        if (uiState.otpInputState.resend) {
            TextButton(
                    modifier = Modifier.offset(y = -(MaterialTheme.spacing.spaceMedium)),
                    onClick = { onEvent(AuthUiEvent.ResendOtp(activity = activity)) }
            ) {
                Text(
                        text = stringResource(id = R.string.txt_btn_resend),
                        style = MaterialTheme.typography.Title3
                )
            }

        } else {

            Text(
                    modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.spaceTen * 2),
                    text = stringResource(
                            id = R.string.cap_text_request_code_in,
                            uiState.otpInputState.secondsRemaining
                    ),
                    style = MaterialTheme.typography.Body3Regular.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            fontFeatureSettings = "tnum"
                    )
            )
        }
    }
}

private fun extractOtp(message: String?): String? {

    Timber.tag("SmsConsentReceiver")
            .i("OtpInputSection - extractOtp() called with $message")
    if (message.isNullOrBlank()) return null

    val match = Regex("""\b(\d{6})\b""").find(message)
    return match?.groupValues?.get(1)
}

@PreviewLightDark
@Composable
private fun OtpInputSection_Preview() {
    val activity = LocalActivity.current ?: return
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {

            OtpInputSection(
                    uiState = AuthUiState(),
                    activity = activity,
                    onEvent = {}
            )
        }
    }
}