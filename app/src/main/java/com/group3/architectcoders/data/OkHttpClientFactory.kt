package com.group3.architectcoders.data

import com.group3.architectcoders.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient

class OkHttpClientFactory {
    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(::apiKeyAsQuery)
            .build()
    }

    private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
        chain.request().newBuilder()
            .url(
                chain.request().url
                    .newBuilder()
                    .addQueryParameter("key", BuildConfig.GOOGLE_BOOKS_API_KEY)
                    .build()
            )
            .build()
    )
}
