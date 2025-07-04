package com.example.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import coil.compose.AsyncImage
import com.example.domain.model.SeasonEpisodeItem
import com.example.domain.model.VodItem

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun <T> ItemCard(
    item: T,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onFocus: () -> Unit = {},
    cardWidth: Dp? = null,
    focusedScale: Float = 1.1f,
    focusedBorderWidth: Dp = 1.dp,
    containerColor: Color = Color.Transparent,
    focusedContainerColor: Color = Color.DarkGray.copy(alpha = 0.3f)
) where T : Any {

    val widthModifier = cardWidth?.let { Modifier.width(it) } ?: Modifier.width(
        (LocalConfiguration.current.screenWidthDp.dp / 5.5f).coerceIn(120.dp, 180.dp)
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .then(widthModifier)
            .aspectRatio(71f / 45f)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    onFocus()
                }
            },
        shape = CardDefaults.shape(RoundedCornerShape(6.dp)),
        scale = CardDefaults.scale(1f, focusedScale = focusedScale),
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(focusedBorderWidth, Color.White),
                shape = RoundedCornerShape(6.dp)
            )
        ),
        colors = CardDefaults.colors(
            containerColor = containerColor,
            focusedContainerColor = focusedContainerColor
        )
    ) {
        val imageUrl = when (item) {
            is SeasonEpisodeItem -> item.thumbnail
            is VodItem -> item.thumbnail
            else -> null
        }
        val contentDesc = when (item) {
            is SeasonEpisodeItem -> item.title
            is VodItem -> item.title
            else -> ""
        }

        AsyncImage(
            model = imageUrl,
            contentDescription = contentDesc,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(Color.DarkGray),
            error = ColorPainter(Color.Red.copy(alpha = 0.5f))
        )
    }
}