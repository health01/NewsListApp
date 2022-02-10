package com.jason.test.newsapp.di

import com.jason.test.newsapp.TestBaseApplication
import com.jason.test.newsapp.di.main.MainFragmentBuildersModule
import com.jason.test.newsapp.di.viewModel.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        DaoModule::class,
        TestNetworkModule::class,
        ActivityBuildersModule::class,
        MainFragmentBuildersModule::class,
        ViewModelFactoryModule::class,
        TestModule::class
    ]
)
interface TestAppComponent : AndroidInjector<TestBaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: TestBaseApplication): Builder
        fun build(): TestAppComponent
    }
}