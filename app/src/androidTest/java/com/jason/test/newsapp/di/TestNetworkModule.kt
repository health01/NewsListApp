package com.jason.test.newsapp.di

import com.google.gson.GsonBuilder
import com.jason.test.newsapp.db.NewsDb
import com.jason.test.newsapp.network.NewsService
import com.jason.test.newsapp.repository.NewsRepository
import com.jason.test.newsapp.repository.db.DbNewsRepository
import com.jason.test.newsapp.util.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class TestNetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val key = "7f71779b-5666-4a12-b93f-d17c91ae07a3"
        val client = OkHttpClient.Builder()
//            .addInterceptor(TokenInterceptor(key))
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(getTestURl())
            .client(client)
            .build()
    }

    private fun getTestURl(): String {
        return "http://localhost:8080/"
    }

    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(db: NewsDb, newsService: NewsService): NewsRepository =
        DbNewsRepository(db ,newsService)


    private class TokenInterceptor(private val apiKey: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var original = chain.request()
            val url = original.url().newBuilder().addQueryParameter("api-key", apiKey).build()
            original = original.newBuilder().url(url).build()
            return chain.proceed(original)
        }
    }
}