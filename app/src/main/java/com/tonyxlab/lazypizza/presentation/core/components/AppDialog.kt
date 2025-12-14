package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.window.DialogProperties
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.theme.LazyPizzaTheme
import com.tonyxlab.lazypizza.presentation.theme.Title1Medium
import com.tonyxlab.lazypizza.presentation.theme.Title3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    positiveButtonText: String,
    negativeButtonText: String? = null,
    modifier: Modifier = Modifier
) {
    AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            confirmButton = {
                AppButton(
                        onClick = onConfirm,
                        buttonText = positiveButtonText
                )
            },
            dismissButton = {
                if (negativeButtonText != null) {
                    TextButton(onClick = onDismiss) {
                        Text(
                                text = negativeButtonText,
                                style = MaterialTheme.typography.Title3
                        )
                    }
                }
            },
            shape = MaterialTheme.shapes.small,
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(
                        text = dialogTitle,
                        style = MaterialTheme.typography.Title1Medium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                        )
                )
            },
            text = {
                if (dialogTitle.isNotBlank()) {
                    Text(
                            text = dialogText,
                            style = MaterialTheme.typography.Title3
                    )
                }
            },
            properties = DialogProperties(usePlatformDefaultWidth = true)
    )
}

@PreviewLightDark
@Composable
private fun AppDialog_Preview() {
    LazyPizzaTheme {
        Column(
                modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {

            AppDialog(
                    modifier = Modifier,
                    onDismiss = {},
                    onConfirm = {},
                    dialogTitle = stringResource(id = R.string.dialog_text_title),
                    dialogText = stringResource(id = R.string.dialog_text_title),
                    positiveButtonText = stringResource(id = R.string.btn_text_log_out),
                    negativeButtonText = stringResource(id = R.string.txt_btn_cancel),
            )
        }
    }
}