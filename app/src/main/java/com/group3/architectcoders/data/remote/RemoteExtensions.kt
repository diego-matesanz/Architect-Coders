package com.group3.architectcoders.data.remote

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.group3.architectcoders.data.remote.RemoteResult.RemoteBook
import com.group3.architectcoders.data.local.Book
import com.group3.architectcoders.utils.toHttps

fun RemoteBook.toLocalModel(): Book =
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
