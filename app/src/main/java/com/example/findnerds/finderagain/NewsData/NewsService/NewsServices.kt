package com.example.findnerds.finderagain.NewsData.NewsService

import com.example.findnerds.finderagain.NewsData.NewsResponse.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.*

interface NewsServices {

    @GET("top-headlines")
    fun getTopHeadlines(@Query("pageSize") pageSize:Int,
                        @Query("country") country: String,
                        @Query("category") category: String,
                        @Query("apikey") apikey: String): retrofit2.Call<NewsResponse>
    @GET("everything")
    fun getEverything(@Query("q") query: String,
                      @Query("apikey") apikey: String,
                      @Query("sortBy") sortBy: String): retrofit2.Call<NewsResponse>

    @GET("top-headlines")
    fun getTopHeadlines(@QueryMap map: Map<String,Objects>): retrofit2.Call<List<NewsResponse>>


}