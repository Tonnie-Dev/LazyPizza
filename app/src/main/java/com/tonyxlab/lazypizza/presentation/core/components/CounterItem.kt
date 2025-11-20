package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.tonyxlab.lazypizza.R
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Title2

@Composable
fun CounterItem(
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    counter: Int,
    modifier: Modifier = Modifier,
    maxCount:Int = 3
) {

    val haptics = LocalHapticFeedback.current

    Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
                modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.small)
                        .border(
                                width = MaterialTheme.spacing.spaceSingleDp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.small
                        )
                        .clickable {
                            haptics.performHapticFeedback(
                                    HapticFeedbackType.LongPress
                            )
                            onRemove()
                        },
                contentAlignment = Alignment.Center
        ) {
            Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = stringResource(id = R.string.cds_text_back),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
                text = counter.toString(),
                style = MaterialTheme.typography.Title2.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                )
        )

        Box(
                modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.small)
                        .border(
                                width = MaterialTheme.spacing.spaceSingleDp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.small
                        )
                        .clickable(enabled = counter < maxCount) {
                            haptics.performHapticFeedback(
                                    HapticFeedbackType.LongPress
                            )
                            onAdd()
                        },
                contentAlignment = Alignment.Center
        ) {
            Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.cds_text_back),
                    tint = if (counter>= maxCount)
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .2f)
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}