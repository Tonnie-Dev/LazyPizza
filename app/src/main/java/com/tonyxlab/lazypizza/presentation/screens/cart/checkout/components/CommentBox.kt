package com.tonyxlab.lazypizza.presentation.screens.cart.checkout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.components.AppInputField
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Body2Regular
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.utils.ifThen

@Composable
fun CommentBox(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    isWideDevice: Boolean = false
) {

    Column(
            modifier = modifier
                    .ifThen(isWideDevice) {
                        padding(bottom = MaterialTheme.spacing.spaceOneTwenty)
                    }.ifThen(isWideDevice.not()){
                        padding(bottom = MaterialTheme.spacing.spaceTen * 4)

                    }) {

        Text(
                modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.spaceTwelve),
                text = stringResource(id = R.string.header_text_comments),
                style = MaterialTheme.typography.Label2SemiBold.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                )

        )

        AppInputField(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.spacing.spaceDoubleDp * 46),
                textFieldState = textFieldState,
                placeholderText = stringResource(id = R.string.placeholder_text_add_comments),
                placeholderTextStyle = MaterialTheme.typography.Body2Regular,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,

                )
    }
}

@Preview
@Composable
private fun CommentBox_Preview() {
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(MaterialTheme.spacing.spaceMedium)
        ) {

            val textFieldState = remember { TextFieldState() }
            CommentBox(textFieldState = textFieldState)
        }
    }
}
