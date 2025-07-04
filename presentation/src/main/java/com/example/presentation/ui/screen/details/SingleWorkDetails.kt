package com.example.presentation.ui.screen.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.presentation.ui.screen.details.components.BackButton
import com.example.presentation.ui.screen.details.components.WatchButton
import com.example.presentation.ui.screen.details.components.Background
import com.example.presentation.ui.screen.details.components.CastInfo
import com.example.presentation.ui.screen.details.components.MetadataInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.domain.model.VodItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.viewmodel.SingleWorkViewModel

@Composable
fun SingleWorkDetails(
    item: VodItem,
    viewModel: SingleWorkViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onWatchPressed: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isLoading) return
    if (uiState.error != null) return

    Box {
        Background(thumbnailUrl = item.thumbnail)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp, start = 48.dp, end = 48.dp)
        ) {
            androidx.tv.material3.Text(
                text = item.title,
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))
            MetadataInfo(item)
            Spacer(modifier = Modifier.height(20.dp))

            androidx.tv.material3.Text(
                text = item.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(24.dp))
            WatchButton(onClick = {
                uiState.data?.streamUrl?.let(onWatchPressed)
            })

            Spacer(modifier = Modifier.height(32.dp))

            item.cast?.takeIf { it.isNotBlank() }?.let { castString ->
                CastInfo(castString = castString)
            }
        }

        BackButton(
            onClick = onBackPressed,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
    }
}

