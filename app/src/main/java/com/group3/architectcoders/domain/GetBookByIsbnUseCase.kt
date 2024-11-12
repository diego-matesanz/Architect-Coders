package com.group3.architectcoders.domain

import com.group3.architectcoders.data.BooksRepository
import com.group3.architectcoders.data.local.Book

class GetBookByIsbnUseCase(val repository: BooksRepository) {
    suspend operator fun invoke(isbn: String): Book = repository.fetchBookByIsbn(isbn)
}
