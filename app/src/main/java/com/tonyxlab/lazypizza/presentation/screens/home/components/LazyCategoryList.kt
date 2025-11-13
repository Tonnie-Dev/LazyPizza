package com.tonyxlab.lazypizza.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold

@Composable
fun <T> LazyCategoryList(
    header: String,
    items: List<T>,
    key: (T) -> Any,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LazyColumn(
            modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
            state = listState
    ) {

        stickyHeader {

            Box(
                    modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.spacing.spaceExtraSmall)

            ) {
                Text(
                        text = header.uppercase(),
                        style = MaterialTheme.typography.Label2SemiBold.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                )
            }
        }

        items(items = items, key = { item -> key(item) }) { item ->

            content(item)
        }
    }
}