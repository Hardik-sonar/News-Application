package com.example.newsapp.model

data class Article(
    val source: Source,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)