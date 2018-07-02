package ru.aleksandrtrushchinskii.surfproject.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.SearchFragmentModule
import ru.aleksandrtrushchinskii.surfproject.common.di.scope.FragmentScope
import ru.aleksandrtrushchinskii.surfproject.ui.search.SearchFragment


@Module
interface MainModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    fun meetupsLineFragment(): SearchFragment

}