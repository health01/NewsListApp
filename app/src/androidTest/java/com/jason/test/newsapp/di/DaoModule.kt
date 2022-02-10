package com.jason.test.newsapp.di

import android.app.Application
import androidx.room.Room
import com.jason.test.newsapp.db.NewsDb
import com.jason.test.newsapp.db.NewsResponseDao
import com.jason.test.newsapp.db.NewsResultDao
import com.jason.test.newsapp.network.NewsService
import com.jason.test.newsapp.repository.NewsRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DaoModule {
    @Named("IO")
    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.Main

    @Singleton
    @Provides
    fun provideDb(app: Application): NewsDb {
//        return Room
//            .databaseBuilder(app, NewsDb::class.java, "testDb.db")
//            .fallbackToDestructiveMigration()
//            .build()

        return Room.inMemoryDatabaseBuilder(
                app,
        NewsDb::class.java
        ).build()
    }

    @Singleton
    @Provides
    fun provideNewsResultDao(db: NewsDb): NewsResultDao {
        return db.newsResultDao()
    }

    @Singleton
    @Provides
    fun provideNewsResponseDao(db: NewsDb): NewsResponseDao {
        return db.newsResponseDao()
    }


}