package com.example.presentation.ui.screen.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Button as TvButton
import androidx.tv.material3.Text as TvText

@Preview(
    name = "Back Button Default",
    showBackground = true,
    backgroundColor = 0xFF222222
)
@Composable
private fun BackButtonPreview() {
    Box(modifier = Modifier.background(Color.DarkGray).padding(16.dp)) {
        BackButton(onClick = {})
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    TvButton(
        onClick = onClick,
        modifier = modifier.size(40.dp),
        shape = ButtonDefaults.shape(CircleShape),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.colors(
            containerColor = Color.LightGray,
            contentColor = Color.White,
            focusedContainerColor = Color.White,
            focusedContentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.DarkGray
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )
    }
}

@Preview(
    name = "Watch Button Default",
    showBackground = true,
    backgroundColor = 0xFF222222
)
@Composable
private fun WatchButtonPreview() {
    Box(modifier = Modifier.background(Color.DarkGray).padding(16.dp)) {
        WatchButton(onClick = {})
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun WatchButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    TvButton(
        onClick = onClick,
        modifier = modifier,
        shape = ButtonDefaults.shape(CircleShape),
        colors = ButtonDefaults.colors(
            containerColor = Color.LightGray,
            contentColor = Color.White,
            focusedContainerColor = Color.White,
            focusedContentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.DarkGray
        )
    ) {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )
        Spacer(Modifier.width(8.dp))
        TvText(
            text = "Watch",
            fontWeight = FontWeight.Bold
        )
    }
}
