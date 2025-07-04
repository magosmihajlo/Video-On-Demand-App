package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SingleWork
import com.example.domain.repository.SingleWorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SingleWorkUiState(
    val isLoading: Boolean = false,
    val data: SingleWork? = null,
    val error: Throwable? = null
)

@HiltViewModel
class SingleWorkViewModel @Inject constructor(
    private val singleWorkRepository: SingleWorkRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SingleWorkUiState())
    val uiState: StateFlow<SingleWorkUiState> = _uiState.asStateFlow()

    init {
        val contentId = checkNotNull(savedStateHandle.get<String>("itemId")) {
            "SingleWorkViewModel requires itemId"
        }
        load(contentId)
    }

    fun load(contentId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = singleWorkRepository.getSingleWorkModel(contentId)
            result.onSuccess { singleWork ->
                _uiState.update {
                    it.copy(isLoading = false, data = singleWork)
                }
            }.onFailure { throwable ->
                _uiState.update { state ->
                    state.copy(isLoading = false, error = throwable)
                }
                Log.e("SingleWorkVM", "Failed to load: ${throwable.message}")
            }
        }
    }
}
