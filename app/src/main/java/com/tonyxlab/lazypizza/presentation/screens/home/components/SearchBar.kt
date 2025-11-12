package com.tonyxlab.lazypizza.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body1Regular
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(
    isEmpty: Boolean,
    textFieldState: TextFieldState,
    expanded: Boolean,
    modifier: Modifier = Modifier
) {

    SearchBar(
            modifier = modifier.shadow(
                    elevation = MaterialTheme.spacing.spaceExtraSmall,
                    shape = RoundedCornerShape(MaterialTheme.spacing.spaceExtraSmall * 7),
                    ambientColor = Color(0x22000000),
                    spotColor = Color(0x22000000)
            ),

            inputField = { InputField(textFieldState = textFieldState, isEmpty = isEmpty) },
            expanded = expanded,
            onExpandedChange = {},
            colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surface)
    ) { }
}

@Composable
private fun InputField(
    textFieldState: TextFieldState,
    isEmpty: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.Body3Regular
) {

    var focused by remember { mutableStateOf(false) }

    Row(
            modifier = modifier.onFocusChanged{ focused = it.isFocused}
                    .height(MaterialTheme.spacing.spaceTwelve * 4)
                    .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                    .padding(vertical = MaterialTheme.spacing.spaceExtraSmall),

            verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
                modifier = Modifier.align(alignment = Alignment.Bottom),
                painter = painterResource(R.drawable.icon_search),
                contentDescription = stringResource(id = R.string.cds_text_search_icon),
        )

        BasicTextField(
                modifier = Modifier
                        // .focusRequester(focusRequester)
                        .fillMaxWidth(),
                state = textFieldState,
                textStyle = textStyle,
                cursorBrush = SolidColor(value = MaterialTheme.colorScheme.primary),
                decorator = { innerTextField ->
                    TextDecorator(isEmpty = isEmpty, innerTextField = innerTextField, focused = focused)
                }
        )
    }
}

@Composable
private fun TextDecorator(
    isEmpty: Boolean,
    focused: Boolean,
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
                    text = stringResource(R.string.placeholder_text_search),
                    style = MaterialTheme.typography.Body1Regular.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            )
        } else {
            innerTextField()
        }
    }
}

@PreviewLightDark
@Composable
fun SearchComponent_Preview(modifier: Modifier = Modifier) {

    val textFieldState = remember { TextFieldState() }
    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            SearchComponent(isEmpty = true, expanded = false, textFieldState = textFieldState)
        }
    }
}