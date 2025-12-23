package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body1Regular
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.utils.InvisibleSpacer
import com.tonyxlab.lazypizza.utils.clickableWithoutRipple

@Composable
fun AppInputField(
    textFieldState: TextFieldState,
    placeholderText: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.Body3Regular,
    placeholderTextStyle: TextStyle = MaterialTheme.typography.Body1Regular,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    shape: Shape = RoundedCornerShape(MaterialTheme.spacing.spaceExtraSmall * 7),
    isSearchField: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    var focused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(
            modifier = modifier
                    .clip(shape)
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.spaceTwelve * 4)
                    .background(
                            color = backgroundColor,
                            shape = shape
                    )
                    .focusRequester(focusRequester)
                    .onFocusChanged { focused = it.isFocused }
                    .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                    .padding(vertical = MaterialTheme.spacing.spaceExtraSmall)
                    .clickableWithoutRipple {
                        focusRequester.requestFocus()
                    },
            contentAlignment = Alignment.CenterStart
    ) {
        Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
        ) {
            if (isSearchField) {
                Column {
                    InvisibleSpacer(componentSize = IntSize(0, 20))
                    Image(
                            painter = painterResource(R.drawable.icon_search),
                            contentDescription = stringResource(id = R.string.cds_text_search_icon),
                    )
                }
            }

            BasicTextField(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MaterialTheme.spacing.spaceExtraSmall),
                    state = textFieldState,
                    textStyle = textStyle.copy(
                            color = MaterialTheme.colorScheme.onSurface
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    keyboardOptions = keyboardOptions,
                    outputTransformation = if (isSearchField.not()) {
                        OutputTransformation {
                            val formatted = formatKenyaPhone(textFieldState.text.toString())
                            replace(0, length, formatted)
                        }}else null,
                    decorator = { innerTextField ->
                        TextDecorator(
                                isEmpty = textFieldState.text.isEmpty(),
                                innerTextField = innerTextField,
                                focused = focused,
                                placeholderText = placeholderText,
                                placeholderTextStyle = placeholderTextStyle.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        )
                    }
            )
        }
    }
}

@Composable
fun TextDecorator(
    isEmpty: Boolean,
    focused: Boolean,
    placeholderText: String,
    placeholderTextStyle: TextStyle,
    innerTextField: @Composable () -> Unit,
    modifier: Modifier = Modifier

) {
    Box(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.spacing.spaceExtraSmall),

            ) {

        if (isEmpty && !focused) {
            Text(
                    text = placeholderText,
                    style = placeholderTextStyle
            )
        } else {
            innerTextField()
        }
    }
}


private fun formatInternationalPhone(raw: String): String {
    val cleaned = raw.replace(" ", "")

    if (!cleaned.startsWith("+")) return cleaned

    val countryCode = cleaned.take(2) // simple assumption: +1, +2, +3…
    val rest = cleaned.drop(2)

    val grouped = rest.chunked(3).joinToString(" ")

    return "$countryCode $grouped"
}

fun formatKenyaPhone(raw: String): String {
    val cleaned = raw.replace(" ", "")

    // Only format Kenya numbers
    if (!cleaned.startsWith("+254")) return cleaned

    val localNumber = cleaned.removePrefix("+254")

    // Guard: Kenya numbers should have up to 9 digits
    val trimmed = localNumber.take(9)

    val grouped = trimmed.chunked(3).joinToString(" ")

    return buildString {
        append("+254")
        if (grouped.isNotEmpty()) {
            append(" ")
            append(grouped)
        }
    }
}

