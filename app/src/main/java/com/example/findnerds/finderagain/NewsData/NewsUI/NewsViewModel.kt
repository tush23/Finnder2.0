package com.example.findnerds.finderagain.NewsData.NewsUI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.findnerds.finderagain.NewsData.NewsResponse.NewsResponse
import com.example.findnerds.finderagain.NewsData.NewsRepos.NewsRepo

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    var newsArticleObservable: LiveData<NewsResponse> = NewsRepo.instance.getTopArticles("in","technology")
    var newsSearchedObservable: LiveData<NewsResponse> = NewsRepo.instance.getSearchedNews("android","popularity")


/*
private fun loadNews(){
    val service = NetworkInstance.retrofitInstance.create(NewsServices::class.java)
    val call = service.getTopHeadlines(100,"in", "technology", NetworkInstance.API_KEY)
    call.enqueue(object : Callback<NewsResponse> {

        override fun onResponse(call: Call<NewsResponse>?, response: Response<NewsResponse>?) {
            dataList(response!!.body())
        }
        override fun onFailure(call: Call<NewsResponse>?, t: Throwable?) {

        }
    })
}
*/
}
