package ru.aleksandrtrushchinskii.surfproject.common.app

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ru.aleksandrtrushchinskii.surfproject.common.di.component.DaggerAppComponent
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
                .builder()
                .context(this)
                .build()
                .inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

}