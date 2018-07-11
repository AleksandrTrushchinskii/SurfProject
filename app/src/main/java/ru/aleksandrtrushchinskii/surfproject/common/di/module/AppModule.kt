package ru.aleksandrtrushchinskii.surfproject.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.aleksandrtrushchinskii.surfproject.common.di.scope.ActivityScope
import ru.aleksandrtrushchinskii.surfproject.ui.MainActivity


@Module(includes = [AndroidSupportInjectionModule::class, SingletonsModule::class])
interface AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, MainActivityModule::class])
    fun mainActivityInjector(): MainActivity

}