package com.tonyxlab.lazypizza.presentation.screens.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body2Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.ifThen

@Composable
fun OtpInput(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier
) {

    val isEmpty = textFieldState.text.isBlank()
    val otp = textFieldState.text
    BasicTextField(
            modifier = modifier.fillMaxWidth(),
            state = textFieldState,
            decorator = { innerTextField ->
                TextDecorator(
                        otp =textFieldState.text.toString(),
                        empty = isEmpty,
                        innerTextField = innerTextField,
                        error = true
                )
            }
    )
}

@Composable
private fun TextDecorator(
    otp: String,
    empty: Boolean,
    error: Boolean,
    innerTextField: @Composable () -> Unit
) {
val otpCode = otp.ifBlank { "000000" }
    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(6) {
            Box(
                    modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .ifThen(error) {
                                border(
                                        width = MaterialTheme.spacing.spaceSingleDp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                )
                            }
                            .height(MaterialTheme.spacing.spaceTwelve * 4)
                            .widthIn(min = MaterialTheme.spacing.spaceTwelve * 4),
                    contentAlignment = Alignment.Center
            ) {

                Text(
                        text = "${otpCode[it]}",
                        style = MaterialTheme.typography.Body2Regular.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun OtpInput_Preview() {
    val textFieldState = remember { TextFieldState() }
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {
            OtpInput(textFieldState = textFieldState)
        }
    }
}

@Composable
private fun OtpInputField(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
            value = otp,
            onValueChange = { newValue ->
                if (newValue.length <= 6 && newValue.all { it.isDigit() }) {
                    onOtpChange(newValue)
                }
            },
            keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
            ),
            decorationBox = {
                Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    repeat(6) { index ->
                        OtpDigitBox(
                                digit = otp.getOrNull(index)
                                        ?.toString() ?: ""
                        )
                    }
                }
            },
            modifier = modifier
    )
}

@Composable
fun OtpDigitBox(digit: String) {
    Box(
            modifier = Modifier
                    .size(48.dp)
                    .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(50)
                    ),
            contentAlignment = Alignment.Center
    ) {
        Text(
                text = digit,
                style = MaterialTheme.typography.titleMedium
        )
    }
}
