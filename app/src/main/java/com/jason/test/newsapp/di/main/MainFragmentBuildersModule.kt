package com.jason.test.newsapp.di.main

import com.jason.test.newsapp.ui.main.news.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector(modules = [NewsFragmentModule::class])
    abstract fun contributeFirstFragment(): NewsFragment
}