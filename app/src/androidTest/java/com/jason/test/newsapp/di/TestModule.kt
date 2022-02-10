package com.jason.test.newsapp.di

import android.app.Application
import com.jason.test.newsapp.TestBaseApplication
import dagger.Binds
import dagger.Module


@Module
interface TestModule {
    @Binds
    fun bindApplication(application: TestBaseApplication): Application
}