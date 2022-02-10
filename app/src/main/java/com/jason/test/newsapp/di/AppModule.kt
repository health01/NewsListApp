package com.jason.test.newsapp.di

import android.app.Application
import androidx.room.Room
import com.jason.test.newsapp.db.NewsDb
import com.jason.test.newsapp.db.NewsResponseDao
import com.jason.test.newsapp.db.NewsResultDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): NewsDb {
        return Room
            .databaseBuilder(app, NewsDb::class.java, "newsDb.db")
            .fallbackToDestructiveMigration()
            .build()
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