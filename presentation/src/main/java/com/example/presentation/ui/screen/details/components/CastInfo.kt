package com.example.presentation.ui.screen.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text as TvText

@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "CastInfo Normal")
@Composable
private fun CastInfoPreview_Normal() {
    Box(Modifier.background(Color.Black).padding(16.dp)) {
        CastInfo(castString = "Actor One, Actor Two, Another Actor, Yet Another Actor")
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "CastInfo Long")
@Composable
private fun CastInfoPreview_Long() {
    Box(Modifier.background(Color.Black).padding(16.dp)) {
        CastInfo(castString = "Very Long Actor Name, Another Very Long Name, This List Keeps Going On And On To Test Wrapping And Ellipsis Handling, Actor 4, Actor 5, Actor 6, Actor 7")
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "CastInfo Empty")
@Composable
private fun CastInfoPreview_Empty() {
    Box(Modifier.background(Color.Black).padding(16.dp)) {
        CastInfo(castString = "")
    }
}

@Composable
internal fun CastInfo(castString: String) {
    val castList = remember(castString) {
        castString.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

    if (castList.isEmpty()) return

    val castDisplayString = remember(castList) {
        castList.joinToString(", ")
    }

    Column {
        TvText(
            text = "Cast",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TvText(
            text = castDisplayString,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}
