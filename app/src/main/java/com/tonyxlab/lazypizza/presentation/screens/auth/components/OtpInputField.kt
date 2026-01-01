package com.tonyxlab.lazypizza.presentation.screens.auth.components

import android.R.attr.textStyle
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.coerceIn
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body2Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme

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
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
            modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused },
            state = textFieldState,
            keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
            ),
            inputTransformation = {
                val filtered = originalText.filter(Char::isDigit)
                        .take(6)
                if (filtered != originalText) {
                    replace(0, originalText.length, filtered)
                }

            },
            decorator = {
                TextDecorator(
                        otp = textFieldState.text.toString(),
                        selectionIndex = textFieldState.selection.start,
                        error = error,
                        textStyle = textStyle,
                        isFocused = isFocused,
                        onBoxClick = { index ->
                            focusRequester.requestFocus()
                            keyboardController?.show()

                            val textLength = textFieldState.text.length
                            val safeIndex = index.coerceAtMost(textLength)

                            textFieldState.edit {
                                if (safeIndex < textLength) {
                                    // ✔ Selecting an existing digit
                                    selection = TextRange(safeIndex, safeIndex + 1)
                                } else {
                                    // ✔ Placing cursor at end / empty box
                                    selection = TextRange(safeIndex)
                                }
                            }
                        }
                )
            }
    )
}

@Composable
private fun TextDecorator(
    otp: String,
    error: Boolean,
    selectionIndex: Int,
    textStyle: TextStyle,
    isFocused: Boolean,
    onBoxClick: (Int) -> Unit,
) {
    val activeIndex = when {
        isFocused -> selectionIndex.coerceIn(0, 5)
        else -> -1
    }

    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(6) { index ->
            val digit = otp.getOrNull(index)
                    ?.toString()
            OtpDigitBox(
                    digit = digit,
                    isActive = isFocused && index == activeIndex,
                    error = error,
                    textStyle = textStyle,
                    onClick = { onBoxClick(index) }
            )
        }
    }
}

@Composable
private fun OtpDigitBox(
    digit: String?,
    isActive: Boolean,
    error: Boolean,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    val cursorAlpha by rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                    animation = tween(500),
                    repeatMode = RepeatMode.Reverse
            )
    )

    Box(
            modifier = Modifier
                    .size(MaterialTheme.spacing.spaceTwelve * 4)
                    .clickable { onClick() }
                    .border(
                            width = MaterialTheme.spacing.spaceSingleDp,
                            color = when {
                                error -> MaterialTheme.colorScheme.error
                                isActive -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.outline
                            },
                            shape = CircleShape
                    )
                    .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(40)
                    ),
            contentAlignment = Alignment.Center
    ) {
        when {
            digit != null -> {
                Text(text = digit, style = textStyle)
            }

            isActive -> {
                Box(
                        modifier = Modifier
                                .width(MaterialTheme.spacing.spaceDoubleDp)
                                .height(MaterialTheme.spacing.spaceTwelve * 2)
                                .background(
                                        color = MaterialTheme.colorScheme.primary.copy(
                                                alpha = cursorAlpha
                                        )
                                )
                )
            }

            else -> {
                Text(
                        text = "0",
                        style = textStyle.copy(
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


