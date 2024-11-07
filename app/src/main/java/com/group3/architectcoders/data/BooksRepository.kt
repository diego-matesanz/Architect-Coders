package com.group3.architectcoders.data

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.group3.architectcoders.data.RemoteResult.RemoteBook


interface BooksRepository {
    suspend fun fetchBooksBySearchText(search: String): List<Book>
    suspend fun fetchBookById(id: String): Book
    suspend fun fetchBookByIsbn(isbn: String): Book
}

class BooksRepositoryImpl : BooksRepository {

    override suspend fun fetchBooksBySearchText(search: String): List<Book> =
        BooksClient
            .instance
            .fetchBooksBySearchText(search)
            .items
            .map { it.toDomainModel() }

    override suspend fun fetchBookById(id: String): Book =
        BooksClient
            .instance
            .fetchBookById(id)
            .toDomainModel()

    override suspend fun fetchBookByIsbn(isbn: String): Book =
        BooksClient
            .instance
            .fetchBooksBySearchText("isbn:$isbn")
            .items
            .first()
            .toDomainModel()
}
