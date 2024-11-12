package com.group3.architectcoders.data

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase



class BooksRepository (private val bookService: BooksService) {

    suspend fun fetchBooksBySearchText(search: String): List<Book> =
        bookService
            .fetchBooksBySearchText(search)
            .items
            .map { it.toDomainModel() }

    suspend fun fetchBookById(id: String): Book =
       bookService
            .fetchBookById(id)
            .toDomainModel()

    suspend fun fetchBookByIsbn(isbn: String): Book =
        bookService
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
