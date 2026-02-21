package com.example.newsapp.network

import com.example.newsapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/v4/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("apikey") apiKey: String
    ): Response<NewsResponse>
}