package com.example.presentation.ui.util

import androidx.compose.ui.focus.FocusRequester

object TvFocusManager {

    fun requestFocusForItem(
        itemId: String?,
        requesters: Map<String, FocusRequester>
    ) {
        itemId?.let { id ->
            requesters[id]?.requestFocus()
        }
    }
}
