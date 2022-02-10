package com.jason.test.newsapp.network

import com.jason.test.newsapp.data.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/search")
    suspend fun search(
        @Query("page") page: Int,
        @Query("q") key: String
    ): News
}