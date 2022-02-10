package com.jason.test.newsapp


import com.jason.test.newsapp.di.AppComponent
import com.jason.test.newsapp.di.DaggerAppComponent
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AppComponent? {
        return DaggerAppComponent.builder().application(this).build()
    }
}