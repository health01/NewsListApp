package com.jason.test.newsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jason.test.newsapp.data.NewsResponse

@Dao
interface NewsResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: NewsResponse)

    @Query("SELECT * FROM news_response WHERE keyWord = :keyword")
    suspend fun getResponseByKeyword(keyword: String): NewsResponse

    @Query("DELETE FROM news_response WHERE keyWord = :searchKey")
    suspend fun deleteByKeyword(searchKey: String)
}