package com.example.presentation.ui.screen.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text as TvText

@Composable
fun TopNavBar(
    onOnDemandClick: () -> Unit,
) {
    val items = listOf("Discover", "Live", "Broadcast", "On Demand")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        items(items.size) { index ->
            val interactionSource = remember { MutableInteractionSource() }

            Box(
                modifier = Modifier
                    .focusable(interactionSource = interactionSource)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (interactionSource.collectIsFocusedAsState().value) Color.White
                        else Color.Transparent
                    )
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        if (items[index] == "On Demand") {
                            onOnDemandClick()
                        }
                    }
                    .padding(vertical = 6.dp, horizontal = 16.dp)
            ) {
                TvText(
                    text = items[index],
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = if (interactionSource.collectIsFocusedAsState().value)
                        FontWeight.Medium else FontWeight.Normal,
                    color = if (interactionSource.collectIsFocusedAsState().value)
                        Color.Black else Color.White
                )
            }
        }
    }
}
