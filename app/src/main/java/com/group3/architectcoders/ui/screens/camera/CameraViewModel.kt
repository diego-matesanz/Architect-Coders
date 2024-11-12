package com.group3.architectcoders.ui.screens.camera

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group3.architectcoders.data.Book
import com.group3.architectcoders.data.BooksRepository
import com.group3.architectcoders.data.BooksRepositoryImpl
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: BooksRepository) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    fun fetchBookByIsbn(isbn: String) {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true, isError = false)
                state = state.copy(isLoading = false, book = repository.fetchBookByIsbn(isbn))
            } catch (_: Exception) {
                state = state.copy(isLoading = false, isError = true)
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val isError: Boolean = false,
    )
}
