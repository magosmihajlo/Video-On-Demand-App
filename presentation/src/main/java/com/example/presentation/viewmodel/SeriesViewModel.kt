package com.example.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SeasonEpisodeItem
import com.example.domain.model.SeasonEpisodes
import com.example.domain.model.SeriesMetadata
import com.example.domain.repository.SeriesMetadataRepository
import com.example.domain.repository.SeriesSeasonsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SeriesUiState(
    val isLoading: Boolean = false,
    val data: SeriesMetadata? = null,
    val error: Throwable? = null
)

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val seriesMetadataRepository: SeriesMetadataRepository,
    private val seriesSeasonsRepository: SeriesSeasonsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeriesUiState())
    val uiState: StateFlow<SeriesUiState> = _uiState.asStateFlow()

    private val _seasons = MutableStateFlow<List<SeasonEpisodes>>(emptyList())
    val seasons: StateFlow<List<SeasonEpisodes>> = _seasons.asStateFlow()

    private val _focusedEpisode = MutableStateFlow<SeasonEpisodeItem?>(null)
    val focusedEpisode: StateFlow<SeasonEpisodeItem?> = _focusedEpisode.asStateFlow()

    init {
        val contentId = checkNotNull(savedStateHandle.get<String>("itemId")) {
            "SeriesViewModel requires itemId"
        }
        load(contentId)
    }

    fun setFocusedEpisode(episode: SeasonEpisodeItem?) {
        _focusedEpisode.value = episode
    }

    fun load(contentId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = seriesMetadataRepository.getSeriesMetadata(contentId)
            result.onSuccess { metadata ->
                _uiState.update {
                    it.copy(isLoading = false, data = metadata)
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(isLoading = false, error = throwable)
                }
                Log.e("SeriesViewModel", "Failed to load metadata: ${throwable.message}")
            }

            val seasonsResult = seriesSeasonsRepository.getSeasons(contentId)
            seasonsResult.onSuccess { seasons ->
                _seasons.value = seasons
                Log.d("SEASONS_API_SUCCESS", "Got ${seasons.size} seasons")
            }.onFailure {
                Log.e("SEASONS_API_FAILURE", "Failed to get seasons", it)
            }
        }
    }
}
