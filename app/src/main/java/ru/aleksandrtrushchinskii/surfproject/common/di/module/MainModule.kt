package ru.aleksandrtrushchinskii.surfproject.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.CreateFragmentModule
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.SearchFragmentModule
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.SignInFragmentModule
import ru.aleksandrtrushchinskii.surfproject.common.di.scope.FragmentScope
import ru.aleksandrtrushchinskii.surfproject.ui.create.CreateFragment
import ru.aleksandrtrushchinskii.surfproject.ui.search.SearchFragment
import ru.aleksandrtrushchinskii.surfproject.ui.signin.SignInFragment


@Module
interface MainModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SignInFragmentModule::class])
    fun signInFragment(): SignInFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CreateFragmentModule::class])
    fun createFragment(): CreateFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    fun searchFragment(): SearchFragment

}