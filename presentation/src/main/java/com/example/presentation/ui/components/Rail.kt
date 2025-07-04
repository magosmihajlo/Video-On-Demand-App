package com.example.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun <T> Rail(
    title: String,
    items: List<T>,
    focusRequesters: MutableMap<String, FocusRequester> = mutableMapOf(),
    itemKey: (T) -> String,
    onItemFocused: (T) -> Unit = {},
    onItemClicked: (T) -> Unit = {},
    itemContent: @Composable (T, Modifier, () -> Unit) -> Unit
) {
    Column {
        if (title.isNotBlank()) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                color = androidx.compose.ui.graphics.Color.White
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items, key = { itemKey(it) }) { item ->
                val key = itemKey(item)
                val focusRequester = focusRequesters.getOrPut(key) { FocusRequester() }

                val modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.hasFocus) onItemFocused(item)
                    }

                itemContent(item, modifier) {
                    onItemClicked(item)
                }
            }
        }
    }
}
