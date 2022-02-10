package com.jason.test.newsapp.di

import com.jason.test.newsapp.di.main.MainFragmentBuildersModule
import com.jason.test.newsapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class])
    abstract fun mainActivity(): MainActivity
}