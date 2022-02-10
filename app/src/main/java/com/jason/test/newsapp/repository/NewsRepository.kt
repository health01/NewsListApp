package com.jason.test.newsapp.repository

import androidx.paging.PagingData
import com.jason.test.newsapp.data.News
import com.jason.test.newsapp.data.NewsResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getDetailFlow(keyWord: String, pageSize: Int): Flow<PagingData<NewsResult>>
}