package com.example.presentation.ui.screen.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text as TvText
import com.example.domain.model.VodItem

@Composable
internal fun MetadataInfo(item: VodItem) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        MetadataRow(
            year = item.year,
            runtime = item.runtime,
            rating = item.rating,
            origRating = item.origRating,
            genre = item.genre
        )

        item.language?.takeIf { it.isNotBlank() }?.let {
            LanguageRow(it)
        }
    }
}

@Composable
private fun MetadataRow(
    year: String?,
    runtime: String?,
    rating: String?,
    origRating: String?,
    genre: String?
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val items = listOfNotNull(
            year?.takeIf { it.isNotBlank() },
            runtime?.takeIf { it.isNotBlank() },
            rating?.takeIf { it.isNotBlank() },
            origRating?.takeIf { it.isNotBlank() }
        ).toMutableList()

        genre?.takeIf { it.isNotBlank() }?.let { g ->
            val formatted = g.split(',')
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .joinToString(" / ")
            if (formatted.isNotBlank()) {
                items.add(formatted)
            }
        }

        items.forEachIndexed { index, value ->
            if (index > 0) MetadataDivider()
            MetadataText(text = value)
        }
    }
}

@Composable
private fun LanguageRow(language: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val formatted = language
            .split(',')
            .map { it.trim().uppercase() }
            .filter { it.isNotEmpty() }
            .take(6)
            .joinToString(" / ")

        MetadataText(
            text = formatted,
            maxLines = 2,
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}

@Composable
internal fun MetadataText(text: String, modifier: Modifier = Modifier, maxLines: Int = 1) {
    TvText(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium,
        color = Color.LightGray,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start
    )
}

@Composable
internal fun MetadataDivider() {
    Text(
        text = "•",
        color = Color.LightGray,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

private val previewMetadataItem = VodItem(
    id = "prevId1",
    title = "Title",
    description = "Desc",
    thumbnail = "",
    streamUrl = "url",
    type = "single-work",
    year = "2024",
    runtime = "1h 45m",
    rating = "12+",
    genre = "Action / Comedy / Very Long Genre Name",
    cast = "Cast",
    language = "EN / ES / FR / DE / IT / JP / RU",
    origRating = "PG-13"
)

@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "Metadata Full")
@Composable
private fun MetadataInfoPreview_Full() {
    Box(Modifier.background(Color.Black).padding(16.dp)) {
        MetadataInfo(item = previewMetadataItem)
    }
}
