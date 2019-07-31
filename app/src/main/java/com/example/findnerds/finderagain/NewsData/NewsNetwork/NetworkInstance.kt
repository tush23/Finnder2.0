package com.example.findnerds.finderagain.NewsData.NewsNetwork

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public object NetworkInstance {
    const val API_KEY = "4b4df2ea3a154950852b6fda536cfb7f"
    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://newsapi.org/v2/"

    val retrofitInstance: Retrofit
        get() {
            if (retrofit == null) {
                val okHttpClient=OkHttpClient.Builder()
                okHttpClient.addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("Api_Key", API_KEY).build()
                    chain.proceed(request)
                }.build()
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!
        }

}