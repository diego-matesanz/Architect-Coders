package com.group3.architectcoders.domain

import com.group3.architectcoders.data.BooksRepository
import com.group3.architectcoders.data.local.Book

class GetBooksBySearchUseCase(val repository: BooksRepository) {
    suspend operator fun invoke(search: String): List<Book> =
        repository.fetchBooksBySearchText(search)
}
