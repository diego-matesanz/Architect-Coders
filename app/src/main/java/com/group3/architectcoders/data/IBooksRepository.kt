package com.group3.architectcoders.data

interface IBooksRepository {
    suspend fun fetchBooksBySearchText(search: String): List<Book>
    suspend fun fetchBookById(id: String): Book
    suspend fun fetchBookByIsbn(isbn: String): Book
}