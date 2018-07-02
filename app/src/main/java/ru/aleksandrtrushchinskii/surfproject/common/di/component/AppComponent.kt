package ru.aleksandrtrushchinskii.surfproject.common.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.aleksandrtrushchinskii.surfproject.common.di.module.AppModule
import ru.aleksandrtrushchinskii.surfproject.common.app.App
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }


    fun inject(app: App)

}