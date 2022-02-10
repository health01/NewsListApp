package com.jason.test.newsapp.mockData

import com.google.gson.Gson
import com.jason.test.newsapp.TestHelper
import com.jason.test.newsapp.data.News
import com.jason.test.newsapp.network.NewsService

class MockNewsService(private val fileName: String) : NewsService {
    override suspend fun search(page: Int, key: String): News {
        val source = TestHelper.getJsonString(fileName)
        val gson = Gson()
        return gson.fromJson(source, News::class.java)
    }
}