package com.example.localmovies.datasource

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {
    fun getClient(): MoviesClient {
        val BASE_URL = "http://192.168.0.101:8080/"
        val httpClient = OkHttpClient.Builder()
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit =
            builder
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        return retrofit.create(MoviesClient::class.java)
    }
}