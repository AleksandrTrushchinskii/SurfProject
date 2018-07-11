package ru.aleksandrtrushchinskii.surfproject.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.*
import ru.aleksandrtrushchinskii.surfproject.common.di.scope.FragmentScope
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.*


@Module
interface MainModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SignInFragmentModule::class])
    fun signInFragment(): SignInFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CreateFragmentModule::class])
    fun createFragment(): CreateFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [EditFragmentModule::class])
    fun editFragment(): EditFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    fun searchFragment(): SearchFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ReadFragmentModule::class])
    fun readFragment(): ReadFragment

}