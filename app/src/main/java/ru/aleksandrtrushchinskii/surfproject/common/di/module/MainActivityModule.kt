package ru.aleksandrtrushchinskii.surfproject.common.di.module

import dagger.Module
import dagger.Provides
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.ui.MainActivity
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation


@Module
class MainActivityModule {

    @Provides
    fun provideNavigation(
            activity: MainActivity,
            auth: Authentication
    ): Navigation = Navigation(activity, auth)

}