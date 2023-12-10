package com.example.core_network

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class NetworkClientCreator {

    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    fun createRetrofitClient(
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createOkHttpClient())
            .build()
    }
}
