package com.example.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text as TvText
import com.example.domain.model.VodItem
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text

private val exampleItem = VodItem("prevId1",
    "Preview Movie Title That Could Be Quite Long",
    "This is a sample description for the preview component. It can be longer than one line, But it can't be longer than 2 lines",
    "",
    "url",
    "single-work",
    "2024",
    "1h 45m",
    "12+",
    "Preview",
    "Prev Actor",
    "EN",
    "PG")

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun ItemDetailsPreview() {
    ItemDetails(item = exampleItem)
}

@Composable
fun ItemDetails(item: VodItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 4.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TvText(
            text = item.title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
