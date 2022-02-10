package com.jason.test.newsapp.repository.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jason.test.newsapp.data.NewsResult
import com.jason.test.newsapp.db.NewsDb
import com.jason.test.newsapp.db.NewsResponseDao
import com.jason.test.newsapp.db.NewsResultDao
import com.jason.test.newsapp.network.NewsService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val db: NewsDb,
    private val newsApi: NewsService,
    private val targetKeyWord: String
) : RemoteMediator<Int, NewsResult>() {
    private val newsResultDao: NewsResultDao = db.newsResultDao()
    private val newsResponseDao: NewsResponseDao = db.newsResponseDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsResult>
    ): MediatorResult {
        try {
            val nextPage = when (loadType) {
                REFRESH -> null
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val filteredResponse = db.withTransaction {
                        newsResponseDao.getResponseByKeyword(targetKeyWord)
                    }

                    if (filteredResponse == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    filteredResponse.currentPage + 1
                }
            }

            val data = newsApi.search(
                page = nextPage ?: 1, key = targetKeyWord
            )

            val items = data.response.results

            db.withTransaction {
                if (loadType == REFRESH) {
                    newsResultDao.deleteByKeyWord(targetKeyWord)
                    newsResponseDao.deleteByKeyword(targetKeyWord)
                }

                newsResponseDao.insert(
                    data.response.copy(keyWord = targetKeyWord)
                )
                newsResultDao.insertAll(items)
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}
