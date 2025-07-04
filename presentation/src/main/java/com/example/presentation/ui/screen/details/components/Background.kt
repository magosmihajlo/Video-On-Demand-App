package com.example.presentation.ui.screen.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Preview(showBackground = true, widthDp = 320, heightDp = 180)
@Composable
private fun BackgroundPreview() {
    val sampleImageUrl = "https://via.placeholder.com/355x225/CCCCCC/000000?text=Background+Preview"
    Box(Modifier.background(Color.DarkGray)) {
        Background(thumbnailUrl = sampleImageUrl)
    }
}

@Composable
internal fun Background(thumbnailUrl: String?) {
    AsyncImage(
        model = thumbnailUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()
                drawRect(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = size.height * 0.1f,
                        endY = size.height
                    )
                )
                drawRect(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent),
                        startX = 0f,
                        endX = size.width * 0.8f
                    )
                )
            }
    )
}
