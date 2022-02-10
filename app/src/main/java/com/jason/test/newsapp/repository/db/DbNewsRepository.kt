package com.jason.test.newsapp.repository.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jason.test.newsapp.db.NewsDb
import com.jason.test.newsapp.network.NewsService
import com.jason.test.newsapp.repository.NewsRepository
import javax.inject.Inject

class DbNewsRepository @Inject constructor(val db: NewsDb, private val newsService: NewsService) :
    NewsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getDetailFlow(keyWord: String, pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(db, newsService, keyWord)
    ) {
        db.newsResultDao().getNewsByKey(keyWord)
    }.flow
}
