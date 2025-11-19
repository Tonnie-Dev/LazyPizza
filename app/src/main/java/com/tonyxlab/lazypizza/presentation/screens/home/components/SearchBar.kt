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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.screens.home.handling.HomeUiState
import com.tonyxlab.lazypizza.presentation.theme.Body1Regular
import com.tonyxlab.lazypizza.presentation.theme.Body3Regular
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.InvisibleSpacer
import com.tonyxlab.lazypizza.utils.clickableWithoutRipple
import com.tonyxlab.lazypizza.utils.mockPizzas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    val textFieldHasText = uiState.textFieldState.text.isNotBlank()

    SearchBar(
            modifier = modifier.wrapContentHeight(),
            inputField = { InputField(textFieldState = uiState.textFieldState) },
            expanded = uiState.textFieldState.text.isNotBlank(),
            onExpandedChange = {},
            colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    dividerColor = Color.Transparent
            ),
    ) {

        LazyColumn(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.spaceSmall)
        ) {

            val items = uiState.searchResults

            if (textFieldHasText && items.isEmpty()) {
                item {
                    Text(
                            modifier = Modifier.padding(MaterialTheme.spacing.spaceSmall),
                            text = stringResource(id = R.string.cap_text_no_results),
                            style = MaterialTheme.typography.titleSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    )
                }
            }

            items(items = items, key = { it.id }) { item ->
                Text(
                        modifier = Modifier.padding(MaterialTheme.spacing.spaceSmall),
                        text = item.name,
                        style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface
                        )
                )
            }
        }
    }
}

@Composable
private fun InputField(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.Body3Regular
) {
    var focused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(
            modifier = modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.spaceTwelve * 4)
                    .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(MaterialTheme.spacing.spaceExtraSmall * 7)
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
            Column {
                InvisibleSpacer(componentSize = IntSize(0, 20))
                Image(
                        painter = painterResource(R.drawable.icon_search),
                        contentDescription = stringResource(id = R.string.cds_text_search_icon),
                )
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
                    decorator = { innerTextField ->
                        TextDecorator(
                                isEmpty = textFieldState.text.isEmpty(),
                                innerTextField = innerTextField,
                                focused = focused
                        )
                    }
            )
        }
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

    LazyPizzaTheme {

        Column(
                modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {

            SearchComponent(
                    uiState = HomeUiState(allPizzaItems = mockPizzas)
            )
        }
    }
}

/*
@Composable
private fun InputFields(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.Body3Regular
) {
    var focused by remember { mutableStateOf(false) }

    Row(
            modifier
                    .clip(shape = RoundedCornerShape(MaterialTheme.spacing.spaceExtraSmall * 7))
                    .onFocusChanged { focused = it.isFocused }
                    .height(MaterialTheme.spacing.spaceSmall *7)
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                    .padding(vertical = MaterialTheme.spacing.spaceExtraSmall),
            verticalAlignment = Alignment.CenterVertically,

    ) {
        Column {
            InvisibleSpacer(componentSize = IntSize(0, 20))
            Image(
                    painter = painterResource(R.drawable.icon_search),
                    contentDescription = stringResource(id = R.string.cds_text_search_icon),
            )
        }

        BasicTextField(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MaterialTheme.spacing.spaceExtraSmall),
                state = textFieldState,
                textStyle = textStyle,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorator = { innerTextField ->
                    TextDecorator(
                            isEmpty = textFieldState.text.isEmpty(),
                            innerTextField = innerTextField,
                            focused = focused
                    )
                }
        )
    }
}
*/
