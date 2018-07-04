package ru.aleksandrtrushchinskii.surfproject.ui

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var auth: Authentication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onStart() {
        super.onStart()

        Navigation.init(this)

        if (auth.isAuth) {
            Navigation.start()
        } else {
            Navigation.startSignIn()
        }
    }

    override fun onStop() {
        super.onStop()

        Navigation.clear()
    }

}