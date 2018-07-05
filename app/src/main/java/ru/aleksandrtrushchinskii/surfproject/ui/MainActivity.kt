package ru.aleksandrtrushchinskii.surfproject.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.databinding.MainActivityBinding
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.signin.SignInFragment
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var auth: Authentication

    private lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity).apply {
            loadingState = LoadingState
            setLifecycleOwner(this@MainActivity)
        }

        Navigation.init(this)

        LoadingState.start()

        if (auth.isAuth) {
            Navigation.start()
        } else {
            Navigation.startFragment(SignInFragment::class.java.simpleName)
        }
    }

    override fun onStart() {
        super.onStart()

        Navigation.init(this)
    }

    override fun onStop() {
        super.onStop()

        Navigation.clear()
    }

}