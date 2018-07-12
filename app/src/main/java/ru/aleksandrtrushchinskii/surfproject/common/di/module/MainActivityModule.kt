package ru.aleksandrtrushchinskii.surfproject.common.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.aleksandrtrushchinskii.surfproject.common.service.Alarm
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.model.database.TodoDatabase
import ru.aleksandrtrushchinskii.surfproject.ui.MainActivity
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation


@Module
class MainActivityModule {

    @Provides
    fun provideNavigation(
            activity: MainActivity,
            auth: Authentication
    ): Navigation = Navigation(activity, auth)


    @Provides
    fun provideAlarm(context: Context, todoDatabase: TodoDatabase) = Alarm(context, todoDatabase)

}