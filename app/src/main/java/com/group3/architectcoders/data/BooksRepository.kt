package com.group3.architectcoders.data

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.group3.architectcoders.data.RemoteResult.RemoteBook

class BooksRepository {

    suspend fun fetchBooksBySearchText(search: String): List<Book> =
        BooksClient
            .instance
            .fetchBooksBySearchText(search)
            .items
            .map { it.toDomainModel() }

    suspend fun fetchBookById(id: String): Book =
        BooksClient
            .instance
            .fetchBookById(id)
            .toDomainModel()

    suspend fun fetchBookByIsbn(isbn: String): Book =
        BooksClient
            .instance
            .fetchBooksBySearchText("isbn:$isbn")
            .items
            .first()
            .toDomainModel()
}

private fun RemoteBook.toDomainModel(): Book =
    Book(
        id = id ?: "",
        title = volumeInfo?.title ?: "",
        authors = volumeInfo?.authors ?: emptyList(),
        coverImage = volumeInfo?.imageLinks?.thumbnail?.toHttps() ?: "",
        pageCount = volumeInfo?.pageCount ?: 0,
        description = volumeInfo?.description ?: "",
        language = volumeInfo?.language?.toUpperCase(Locale.current) ?: "",
        averageRating = volumeInfo?.averageRating ?: 0.0,
        ratingsCount = volumeInfo?.ratingsCount ?: 0
    )
