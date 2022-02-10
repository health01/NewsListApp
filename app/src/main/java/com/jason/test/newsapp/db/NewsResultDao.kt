package com.jason.test.newsapp.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jason.test.newsapp.data.NewsResult

@Dao
interface NewsResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<NewsResult>)

    @Query("select * from news_result where  webTitle LIKE '%' || :keyword || '%' ")
    fun getNewsByKey(keyword: String): PagingSource<Int, NewsResult>

    @Query("select * from news_result where  webTitle LIKE '%' || :keyword || '%' ")
    fun getNewsListByKey(keyword: String): List<NewsResult>

    @Query("DELETE FROM news_result WHERE webTitle LIKE '%' || :keyword || '%'")
    suspend fun deleteByKeyWord(keyword: String)
}
