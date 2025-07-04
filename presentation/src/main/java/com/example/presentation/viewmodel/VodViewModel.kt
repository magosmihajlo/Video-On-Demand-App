package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AdvertisingIdRepository
import com.example.domain.repository.AuidRepository
import com.example.domain.repository.VodListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.util.Log
import com.example.domain.model.RailItem
import com.example.domain.model.SeriesMetadata
import com.example.domain.model.SingleWork
import com.example.domain.model.VodItem
import com.example.domain.repository.SeriesMetadataRepository
import com.example.domain.repository.SingleWorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class VodUiState(
    val rails: List<RailItem> = emptyList(),
    val focusedItem: VodItem? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
// IMPORTANT PART:

@HiltViewModel
class VodViewModel @Inject constructor(
    private val vodListRepository: VodListRepository,
    private val singleWorkRepository: SingleWorkRepository,
    private val seriesMetadataRepository: SeriesMetadataRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VodUiState())
    val uiState: StateFlow<VodUiState> = _uiState.asStateFlow()

    private val _selectedItem = MutableStateFlow<VodItem?>(null)
    val selectedItem: StateFlow<VodItem?> = _selectedItem.asStateFlow()

    init {
        loadInitialData()
    }

    fun updateFocusedItem(item: VodItem?) {
        Log.d("TAG", "updateFocusedItem: $item")
        _uiState.update { it.copy(focusedItem = item) }
    }

    fun setSelectedItem(item: VodItem?) {
        _selectedItem.value = item
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val railsResult = vodListRepository.getVodListModel()
                // Debugging
                val fetchedRails = railsResult.getOrThrow()

                _uiState.update {
                    it.copy(rails = fetchedRails, isLoading = false)
                }

            } catch (exception: Throwable) {
                _uiState.update {
                    it.copy(error = exception, isLoading = false)
                }
            }
        }
    }
}