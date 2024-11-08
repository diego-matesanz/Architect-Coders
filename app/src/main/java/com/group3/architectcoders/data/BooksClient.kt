package com.group3.architectcoders.data

import kotlinx.serialization.json.Json
import retrofit2.create

object BooksClient {
    private val okHttpClient = OkHttpClientFactory().createOkHttpClient()
    private val json = Json {
        ignoreUnknownKeys = true
    }
    val instance = RetrofitClient(okHttpClient, json).createRetrofit().create<BooksService>()
}
