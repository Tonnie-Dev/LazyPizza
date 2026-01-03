package com.tonyxlab.lazypizza.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.tonyxlab.lazypizza.presentation.core.utils.spacing
import com.tonyxlab.lazypizza.presentation.theme.Label2SemiBold

@Composable
fun <T> LazyListComponent(
    items: List<T>,
    key: (T) -> Any,
    header: String = "",
    modifier: Modifier = Modifier,
    isDeviceWide: Boolean = false,
    content: @Composable (T) -> Unit,
) {
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }
    val gridState = rememberSaveable(saver = LazyGridState.Saver) {
        LazyGridState()
    }
    when{

        isDeviceWide -> {

            LazyVerticalGrid (
                    modifier = modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize(),
                    columns = GridCells.Fixed(count = 2),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
                    state = gridState
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
        else -> {


            LazyColumn(
                    modifier = modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall),
                    state = listState
            ) {

                if (header.isNotBlank()) {
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
                }

                items(items = items, key = { item -> key(item) }) { item ->
                    content(item)
                }
            }
        }
    }

}