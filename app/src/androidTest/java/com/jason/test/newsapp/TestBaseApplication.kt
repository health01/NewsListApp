package com.jason.test.newsapp

import com.jason.test.newsapp.di.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class TestBaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerTestAppComponent.builder().application(this).build()
    }
}