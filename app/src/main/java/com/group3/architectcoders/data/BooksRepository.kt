package com.group3.architectcoders.data

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.group3.architectcoders.data.RemoteResult.RemoteBook


interface BooksRepository {
    suspend fun fetchBooksBySearchText(search: String): List<Book>
    suspend fun fetchBookById(id: String): Book
    suspend fun fetchBookByIsbn(isbn: String): Book
}

class BooksRepositoryImpl(private val service: BooksService) : BooksRepository {

    override suspend fun fetchBooksBySearchText(search: String): List<Book> =
        service
            .fetchBooksBySearchText(search)
            .items
            .map { it.toDomainModel() }

    override suspend fun fetchBookById(id: String): Book =
        service
            .fetchBookById(id)
            .toDomainModel()

    override suspend fun fetchBookByIsbn(isbn: String): Book =
        service
            .fetchBooksBySearchText("isbn:$isbn")
            .items
            .first()
            .toDomainModel()
}
