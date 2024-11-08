package com.group3.architectcoders.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group3.architectcoders.data.local.Book
import com.group3.architectcoders.data.BooksClient
import com.group3.architectcoders.data.BooksRepository
import com.group3.architectcoders.domain.GetBooksBySearchUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getBooksBySearchUseCase: GetBooksBySearchUseCase,
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    fun fetchBooksBySearch(search: String) {
        viewModelScope.launch {
            try {
                state = UiState(isLoading = true, searchText = search, isError = false)
                state = UiState(
                    isLoading = false,
                    books = getBooksBySearchUseCase(search),
                )
            } catch (_: Exception) {
                state = UiState(isLoading = false, isError = true)
            }
        }
    }

    data class UiState(
        val books: List<Book> = emptyList(),
        val isLoading: Boolean = false,
        val searchText: String = "",
        val isError: Boolean = false,
    )
}
