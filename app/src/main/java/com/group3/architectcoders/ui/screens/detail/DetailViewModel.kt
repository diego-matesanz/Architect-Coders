package com.group3.architectcoders.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group3.architectcoders.data.Book
import com.group3.architectcoders.data.BooksRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val id: String) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    private val repository = BooksRepository()

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            state = state.copy(isLoading = false, book = repository.fetchBookById(id))
        }
    }

    fun onDominantColor(color: Int) {
        state = state.copy(dominantColor = color)
    }

    data class UiState(
        val isLoading: Boolean = false,
        val book: Book? = null,
        val dominantColor: Int = 0,
    )
}
