package com.group3.architectcoders.data

import com.group3.architectcoders.data.local.Book
import com.group3.architectcoders.data.remote.toLocalModel

class BooksRepository(val service: BooksService) {

    suspend fun fetchBooksBySearchText(search: String): List<Book> =
        service
            .fetchBooksBySearchText(search)
            .items
            .map { it.toLocalModel() }

    suspend fun fetchBookById(id: String): Book =
        service
            .fetchBookById(id)
            .toLocalModel()

    suspend fun fetchBookByIsbn(isbn: String): Book =
        service
            .fetchBooksBySearchText("isbn:$isbn")
            .items
            .first()
            .toLocalModel()
}
