package com.example.findnerds.finderagain.NewsData.NewsResponse

import com.example.findnerds.finderagain.NewsData.NewsModel.ArticleModel

class NewsResponse (
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleModel>
)