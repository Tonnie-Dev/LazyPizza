package com.tonyxlab.lazypizza.presentation.screens.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body2Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.ifThen

@Composable
fun OtpInput(
    textFieldState: TextFieldState,
    error: Boolean,
    modifier: Modifier = Modifier
) {
   val textStyle = MaterialTheme.typography.Body2Regular.copy(
            color = if (textFieldState.text.isBlank())
                MaterialTheme.colorScheme.onSurfaceVariant
            else
                MaterialTheme.colorScheme.onSurface
        )
    BasicTextField(
            modifier = modifier.fillMaxWidth(),
            state = textFieldState,
            keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
            ),
            decorator = {
                TextDecorator(
                        otp = textFieldState.text.toString(),
                        error = error,
                        textStyle = textStyle
                )
            }
    )
}

@Composable
private fun TextDecorator(
    otp: String,
    error: Boolean,
    textStyle: TextStyle
) {

    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(6) { i ->

            val char = otp.getOrNull(i) ?: 0
            OtpDigitBox(
                    digit = char.toString(),
                    textStyle = textStyle,
                    error = error
            )
        }
    }
}

@Composable
private fun OtpDigitBox(
    digit: String,
    error: Boolean,
    textStyle: TextStyle
) {
    Box(
            modifier = Modifier
                    .size(MaterialTheme.spacing.spaceTwelve * 4)
                    .ifThen(error) {
                        border(
                                width = MaterialTheme.spacing.spaceSingleDp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                        )
                    }
                    .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(40)
                    ),
            contentAlignment = Alignment.Center
    ) {
        Text(
                text = digit,
                style = textStyle
        )
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

            OtpInput(
                    textFieldState = textFieldState,
                    error = false
            )

            OtpInput(
                    textFieldState = textFieldState,
                    error = false
            )
        }
    }
}


