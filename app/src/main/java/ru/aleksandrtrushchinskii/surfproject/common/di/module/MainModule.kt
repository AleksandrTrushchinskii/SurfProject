package ru.aleksandrtrushchinskii.surfproject.common.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.CreateFragmentModule
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.EditFragmentModule
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.SearchFragmentModule
import ru.aleksandrtrushchinskii.surfproject.common.di.module.ui.SignInFragmentModule
import ru.aleksandrtrushchinskii.surfproject.common.di.scope.FragmentScope
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.CreateFragment
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.EditFragment
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.SearchFragment
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.SignInFragment


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

    @FragmentScope
    @ContributesAndroidInjector(modules = [EditFragmentModule::class])
    fun editFragment(): EditFragment

}