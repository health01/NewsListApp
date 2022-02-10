package com.jason.test.newsapp.di.main

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class NewsFragmentModule {
    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO
}