package com.jason.test.newsapp.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.jason.test.newsapp.data.NewsResponse
import com.jason.test.newsapp.data.NewsResult

@Database(
    entities = [
        NewsResponse::class,
        NewsResult::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDb : RoomDatabase() {

    abstract fun newsResponseDao(): NewsResponseDao

    abstract fun newsResultDao(): NewsResultDao
}
