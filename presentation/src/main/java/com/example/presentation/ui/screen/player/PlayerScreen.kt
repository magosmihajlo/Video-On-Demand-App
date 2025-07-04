package com.example.presentation.ui.screen.player

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import com.example.presentation.ui.screen.player.components.ExoPlayerView


@Composable
fun PlayerScreen(
    streamUrl: String,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ExoPlayerView(
            url = streamUrl,
            modifier = Modifier.fillMaxSize()
        )
    }
}
