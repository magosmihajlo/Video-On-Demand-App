package com.example.presentation.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.VodItem
import com.example.presentation.viewmodel.SeriesViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.presentation.ui.screen.details.components.BackButton
import com.example.presentation.ui.components.PositionFocusedItemInLazyLayout
import com.example.presentation.ui.components.Rail
import com.example.presentation.ui.screen.details.components.WatchButton
import com.example.presentation.ui.screen.details.components.Background
import com.example.presentation.ui.screen.details.components.CastInfo
import com.example.presentation.ui.screen.details.components.MetadataInfo
import com.example.presentation.ui.components.ItemCard
import com.example.presentation.ui.components.ItemDetailsBar

@Composable
fun SeriesDetails(
    item: VodItem,
    viewModel: SeriesViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onWatchPressed: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val seasons by viewModel.seasons.collectAsStateWithLifecycle()
    val focusedEpisode by viewModel.focusedEpisode.collectAsStateWithLifecycle()


    if (uiState.isLoading || uiState.data == null) return
    if (uiState.error != null) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Background(thumbnailUrl = item.thumbnail)

        PositionFocusedItemInLazyLayout(
            parentFraction = 0.25f,
            childFraction = 0f,
            additionalFocusPadding = 16.dp
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 48.dp),
                contentPadding = PaddingValues(top = 20.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    BackButton(
                        onClick = onBackPressed,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                }

                item {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                item { MetadataInfo(item) }

                item {
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                item {
                    WatchButton(
                        onClick = { onWatchPressed(item.streamUrl) },
                        modifier = Modifier
                            .onFocusChanged { focusState ->
                                if (focusState.hasFocus) {
                                    viewModel.setFocusedEpisode(null)
                                }
                            }
                    )
                }

                item {
                    item.cast?.takeIf { it.isNotBlank() }?.let {
                        CastInfo(it)
                    }
                }


                items(seasons, key = { it.seasonNumber }) { season ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Season ${season.seasonNumber}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )


                        if (focusedEpisode != null && season.episodes.any { it.contentId == focusedEpisode!!.contentId }) {
                            ItemDetailsBar(
                                item = focusedEpisode,
                                height = 80.dp,
                                padding = PaddingValues(bottom = 8.dp)
                            ) { currentEpisode ->
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = currentEpisode.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White
                                    )
                                    Text(
                                        text = currentEpisode.description,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.White.copy(alpha = 0.9f),
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }


                        Rail(
                            title = "",
                            items = season.episodes,
                            itemKey = { it.contentId },
                            onItemFocused = { episode ->
                                viewModel.setFocusedEpisode(episode)
                            },
                            onItemClicked = { episode ->
                                episode.vodPlaybackUrl?.let { onWatchPressed(it) }
                            }
                        ) { episode, modifier, onClick ->
                            ItemCard(
                                item = episode,
                                modifier = modifier,
                                onClick = onClick,
                                cardWidth = 160.dp,
                                focusedScale = 1.1f,
                                focusedBorderWidth = 1.dp,
                                containerColor = Color.Transparent,
                                focusedContainerColor = Color.DarkGray.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }
        }
    }
}
