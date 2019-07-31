package com.example.findnerds.finderagain.NewsData.NewsRepos

import androidx.lifecycle.LiveData
import com.example.findnerds.finderagain.NewsData.NewsNetwork.NetworkInstance
import com.example.findnerds.finderagain.NewsData.NewsResponse.NewsResponse
import com.example.findnerds.finderagain.NewsData.NewsService.NewsServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.MutableLiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


public class NewsRepo{

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        newServices = retrofit.create(NewsServices::class.java!!)
    }


    fun getTopArticles(pageSize:Int,country:String,category:String):LiveData<NewsResponse>{
        val data = MutableLiveData<NewsResponse>()
        val service = NetworkInstance.retrofitInstance.create(NewsServices::class.java)
        val call = service.getTopHeadlines(pageSize,country, category, NetworkInstance.API_KEY)
        call.enqueue(object : Callback<NewsResponse> {

            override fun onResponse(call: Call<NewsResponse>?, response: Response<NewsResponse>?) {
                data.value = response!!.body()
            }
            override fun onFailure(call: Call<NewsResponse>?, t: Throwable?) {

            }
        })
        return data
    }

    fun getSearchedNews(q:String,popularity:String):LiveData<NewsResponse>{
        val data=MutableLiveData<NewsResponse>()
        val service = NetworkInstance.retrofitInstance.create(NewsServices::class.java)
        val call = service.getEverything(q, NetworkInstance.API_KEY,popularity)
        call.enqueue(object : Callback<NewsResponse> {

            override fun onResponse(call: Call<NewsResponse>?, response: Response<NewsResponse>?) {
                data.value = response!!.body()
            }
            override fun onFailure(call: Call<NewsResponse>?, t: Throwable?) {

            }
        })
        return data
    }
    companion object {
        @JvmStatic
        private var newsRepo: NewsRepo? = null
        @JvmStatic
        private val BASE_URL = "https://newsapi.org/v2/"
        @JvmStatic
        var newServices: NewsServices? = null

        val instance: NewsRepo
            @Synchronized get() {
                if (newsRepo == null) {
                    if (newsRepo == null) {
                        newsRepo = NewsRepo()
                    }
                }
                return newsRepo as NewsRepo
            }
    }

}