package com.group3.architectcoders.domain

import com.group3.architectcoders.data.BooksRepository
import com.group3.architectcoders.data.local.Book

class GetBookByIdUseCase(val repository: BooksRepository) {
    suspend operator fun invoke(id: String): Book = repository.fetchBookById(id)
}
