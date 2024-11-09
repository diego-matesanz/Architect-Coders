package com.group3.architectcoders.data.local

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val coverImage: String,
    val pageCount: Int,
    val description: String,
    val language: String,
    val averageRating: Double,
    val ratingsCount: Int,
)
